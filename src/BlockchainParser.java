import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.gargoylesoftware.htmlunit.AlertHandler;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.*;

public class BlockchainParser {

	HashMap<String, int[]> opcodes;
	HashMap<String, HashMap<String, Integer>> versions;
	ArrayList<String> v = new ArrayList<String>();
	
		
	public BlockchainParser() {
		opcodes = new HashMap<String, int[]>();
		this.versions = new HashMap<String,  HashMap<String, Integer>>();
	}

	/**
	 * Versioni del compilatore Solidity da analizzare
	 */
	public void insertVersions() {
		v.add("0.1.2");
		v.add("0.1.3");
		v.add("0.1.4");
		v.add("0.1.4");
		v.add("0.1.5");
		v.add("0.1.6");
		v.add("0.1.7");
		v.add("0.2.0");
		v.add("0.2.1");
		v.add("0.2.2");
		v.add("0.3.0");
		v.add("0.3.1");
		v.add("0.3.2");
		v.add("0.3.3");
		v.add("0.3.4");
		v.add("0.3.5");
		v.add("0.3.6");
		v.add("0.4.0");
		v.add("0.4.1");
		v.add("0.4.2");
		v.add("0.4.3");
		v.add("0.4.4");
		v.add("0.4.5");
		v.add("0.4.6");
		v.add("0.4.7");
		v.add("0.4.8");
		v.add("0.4.9");
		v.add("0.4.10");
		v.add("0.4.11");
		v.add("0.4.12");
		v.add("0.4.13");
		v.add("0.4.14");
		v.add("0.4.15");
		v.add("0.4.16");
		v.add("0.4.17");
		v.add("0.4.18");
		v.add("0.4.19");
		v.add("0.4.20");
		v.add("0.4.21");
		v.add("0.4.22");
		v.add("0.4.23");
		v.add("0.4.24");
		v.add("0.4.25");
	}
	
	/**
	 * Crea l'Hashmap per ogni opcode e inizializza i valori a zero.
	 * I quattro valori rappresentano: "numerosità da non verificati", "numerosità da verificati", 
	 * "numerosità da verificati con solidity", "numerosità da verificati non da solidity"
	 */
	public void createOpcodesMap() {
		opcodes.put("STOP", new int[] {0,0,0,0});
		opcodes.put("ADD", new int[] {0,0,0,0});
		opcodes.put("MUL", new int[] {0,0,0,0});
		opcodes.put("SUB", new int[] {0,0,0,0});
		opcodes.put("DIV", new int[] {0,0,0,0});
		opcodes.put("SDIV", new int[] {0,0,0,0});
		opcodes.put("MOD", new int[] {0,0,0,0});
		opcodes.put("SMOD", new int[] {0,0,0,0});
		opcodes.put("ADDMOD", new int[] {0,0,0,0});
		opcodes.put("MULMOD", new int[] {0,0,0,0});
		opcodes.put("EXP", new int[] {0,0,0,0});
		opcodes.put("SIGNEXTEND", new int[] {0,0,0,0});
		opcodes.put("LT", new int[] {0,0,0,0});
		opcodes.put("GT", new int[] {0,0,0,0});
		opcodes.put("SLT", new int[] {0,0,0,0});
		opcodes.put("SGT", new int[] {0,0,0,0});
		opcodes.put("EQ", new int[] {0,0,0,0});
		opcodes.put("ISZERO", new int[] {0,0,0,0});
		opcodes.put("AND", new int[] {0,0,0,0});
		opcodes.put("OR", new int[] {0,0,0,0});
		opcodes.put("XOR", new int[] {0,0,0,0});
		opcodes.put("NOT", new int[] {0,0,0,0});
		opcodes.put("BYTE", new int[] {0,0,0,0});
		opcodes.put("SHA3", new int[] {0,0,0,0});
		opcodes.put("ADDRESS", new int[] {0,0,0,0});
		opcodes.put("BALANCE", new int[] {0,0,0,0});
		opcodes.put("ORIGIN", new int[] {0,0,0,0});
		opcodes.put("CALLER", new int[] {0,0,0,0});
		opcodes.put("CALLVALUE", new int[] {0,0,0,0});
		opcodes.put("CALLDATALOAD", new int[] {0,0,0,0});
		opcodes.put("CALLDATASIZE", new int[] {0,0,0,0});
		opcodes.put("CALLDATACOPY", new int[] {0,0,0,0});
		opcodes.put("CODESIZE", new int[] {0,0,0,0});
		opcodes.put("CODECOPY", new int[] {0,0,0,0});
		opcodes.put("GASPRICE", new int[] {0,0,0,0});
		opcodes.put("EXTCODESIZE", new int[] {0,0,0,0});
		opcodes.put("EXTCODECOPY", new int[] {0,0,0,0});
		opcodes.put("BLOCKHASH", new int[] {0,0,0,0});
		opcodes.put("COINBASE", new int[] {0,0,0,0});
		opcodes.put("TIMESTAMP", new int[] {0,0,0,0});
		opcodes.put("NUMBER", new int[] {0,0,0,0});
		opcodes.put("DIFFICULTY", new int[] {0,0,0,0});
		opcodes.put("GASLIMIT", new int[] {0,0,0,0});
		opcodes.put("POP", new int[] {0,0,0,0});
		opcodes.put("MLOAD", new int[] {0,0,0,0});
		opcodes.put("MSTORE", new int[] {0,0,0,0});
		opcodes.put("MSTORE8", new int[] {0,0,0,0});
		opcodes.put("SLOAD", new int[] {0,0,0,0});
		opcodes.put("SSTORE", new int[] {0,0,0,0});
		opcodes.put("JUMP", new int[] {0,0,0,0});
		opcodes.put("JUMPI", new int[] {0,0,0,0});
		opcodes.put("PC", new int[] {0,0,0,0});
		opcodes.put("MSIZE", new int[] {0,0,0,0});
		opcodes.put("GAS", new int[] {0,0,0,0});
		opcodes.put("JUMPDEST", new int[] {0,0,0,0});
		opcodes.put("PUSH1", new int[] {0,0,0,0});
		opcodes.put("PUSH2", new int[] {0,0,0,0});
		opcodes.put("PUSH3", new int[] {0,0,0,0});
		opcodes.put("PUSH4", new int[] {0,0,0,0});
		opcodes.put("PUSH5", new int[] {0,0,0,0});
		opcodes.put("PUSH6", new int[] {0,0,0,0});
		opcodes.put("PUSH7", new int[] {0,0,0,0});
		opcodes.put("PUSH8", new int[] {0,0,0,0});
		opcodes.put("PUSH9", new int[] {0,0,0,0});
		opcodes.put("PUSH10", new int[] {0,0,0,0});
		opcodes.put("PUSH11", new int[] {0,0,0,0});
		opcodes.put("PUSH12", new int[] {0,0,0,0});
		opcodes.put("PUSH13", new int[] {0,0,0,0});
		opcodes.put("PUSH14", new int[] {0,0,0,0});
		opcodes.put("PUSH15", new int[] {0,0,0,0});
		opcodes.put("PUSH16", new int[] {0,0,0,0});
		opcodes.put("PUSH17", new int[] {0,0,0,0});
		opcodes.put("PUSH18", new int[] {0,0,0,0});
		opcodes.put("PUSH19", new int[] {0,0,0,0});
		opcodes.put("PUSH20", new int[] {0,0,0,0});
		opcodes.put("PUSH21", new int[] {0,0,0,0});
		opcodes.put("PUSH22", new int[] {0,0,0,0});
		opcodes.put("PUSH23", new int[] {0,0,0,0});
		opcodes.put("PUSH24", new int[] {0,0,0,0});
		opcodes.put("PUSH25", new int[] {0,0,0,0});
		opcodes.put("PUSH26", new int[] {0,0,0,0});
		opcodes.put("PUSH27", new int[] {0,0,0,0});
		opcodes.put("PUSH28", new int[] {0,0,0,0});
		opcodes.put("PUSH29", new int[] {0,0,0,0});
		opcodes.put("PUSH30", new int[] {0,0,0,0});
		opcodes.put("PUSH31", new int[] {0,0,0,0});
		opcodes.put("PUSH32", new int[] {0,0,0,0});
		opcodes.put("DUP1", new int[] {0,0,0,0});
		opcodes.put("DUP2", new int[] {0,0,0,0});
		opcodes.put("DUP3", new int[] {0,0,0,0});
		opcodes.put("DUP4", new int[] {0,0,0,0});
		opcodes.put("DUP5", new int[] {0,0,0,0});
		opcodes.put("DUP6", new int[] {0,0,0,0});
		opcodes.put("DUP7", new int[] {0,0,0,0});
		opcodes.put("DUP8", new int[] {0,0,0,0});
		opcodes.put("DUP9", new int[] {0,0,0,0});
		opcodes.put("DUP10", new int[] {0,0,0,0});
		opcodes.put("DUP11", new int[] {0,0,0,0});
		opcodes.put("DUP12", new int[] {0,0,0,0});
		opcodes.put("DUP13", new int[] {0,0,0,0});
		opcodes.put("DUP14", new int[] {0,0,0,0});
		opcodes.put("DUP15", new int[] {0,0,0,0});
		opcodes.put("DUP16", new int[] {0,0,0,0});
		opcodes.put("SWAP1", new int[] {0,0,0,0});
		opcodes.put("SWAP2", new int[] {0,0,0,0});
		opcodes.put("SWAP3", new int[] {0,0,0,0});
		opcodes.put("SWAP4", new int[] {0,0,0,0});
		opcodes.put("SWAP5", new int[] {0,0,0,0});
		opcodes.put("SWAP6", new int[] {0,0,0,0});
		opcodes.put("SWAP7", new int[] {0,0,0,0});
		opcodes.put("SWAP8", new int[] {0,0,0,0});
		opcodes.put("SWAP9", new int[] {0,0,0,0});
		opcodes.put("SWAP10", new int[] {0,0,0,0});
		opcodes.put("SWAP11", new int[] {0,0,0,0});
		opcodes.put("SWAP12", new int[] {0,0,0,0});
		opcodes.put("SWAP13", new int[] {0,0,0,0});
		opcodes.put("SWAP14", new int[] {0,0,0,0});
		opcodes.put("SWAP15", new int[] {0,0,0,0});
		opcodes.put("SWAP16", new int[] {0,0,0,0});
		opcodes.put("LOG0", new int[] {0,0,0,0});
		opcodes.put("LOG1", new int[] {0,0,0,0});
		opcodes.put("LOG2", new int[] {0,0,0,0});
		opcodes.put("LOG3", new int[] {0,0,0,0});
		opcodes.put("LOG4", new int[] {0,0,0,0});
		opcodes.put("CREATE", new int[] {0,0,0,0});
		opcodes.put("CALL", new int[] {0,0,0,0});
		opcodes.put("CALLCODE", new int[] {0,0,0,0});
		opcodes.put("RETURN", new int[] {0,0,0,0});
		opcodes.put("DELEGATECALL", new int[] {0,0,0,0});
		opcodes.put("STATICCAL", new int[] {0,0,0,0});
		opcodes.put("REVERT", new int[] {0,0,0,0});
		opcodes.put("INVALID", new int[] {0,0,0,0});
		opcodes.put("SELFDESTRUCT", new int[] {0,0,0,0});		
	}
	
	/**
	 * Crea una HashMap in cui per ogni versione del compilatore solidity
	 * si memorizza la numerosità di ogni opcode
	 */
	public void createVersionsMap() {
		versions.put("STOP", new HashMap<String, Integer>());
		versions.put("ADD", new HashMap<String, Integer>());
		versions.put("MUL", new HashMap<String, Integer>());
		versions.put("SUB", new HashMap<String, Integer>());
		versions.put("DIV", new HashMap<String, Integer>());
		versions.put("SDIV", new HashMap<String, Integer>());
		versions.put("MOD", new HashMap<String, Integer>());
		versions.put("SMOD", new HashMap<String, Integer>());
		versions.put("ADDMOD", new HashMap<String, Integer>());
		versions.put("MULMOD", new HashMap<String, Integer>());
		versions.put("EXP", new HashMap<String, Integer>());
		versions.put("SIGNEXTEND", new HashMap<String, Integer>());
		versions.put("LT", new HashMap<String, Integer>());
		versions.put("GT", new HashMap<String, Integer>());
		versions.put("SLT", new HashMap<String, Integer>());
		versions.put("SGT", new HashMap<String, Integer>());
		versions.put("EQ", new HashMap<String, Integer>());		
		versions.put("ISZERO", new HashMap<String, Integer>());
		versions.put("AND", new HashMap<String, Integer>());
		versions.put("OR", new HashMap<String, Integer>());
		versions.put("XOR", new HashMap<String, Integer>());
		versions.put("NOT", new HashMap<String, Integer>());
		versions.put("BYTE", new HashMap<String, Integer>());
		versions.put("SHA3", new HashMap<String, Integer>());
		versions.put("ADDRESS", new HashMap<String, Integer>());
		versions.put("BALANCE", new HashMap<String, Integer>());
		versions.put("ORIGIN", new HashMap<String, Integer>());
		versions.put("CALLER", new HashMap<String, Integer>());
		versions.put("CALLVALUE", new HashMap<String, Integer>());
		versions.put("CALLDATALOAD", new HashMap<String, Integer>());
		versions.put("CALLDATASIZE", new HashMap<String, Integer>());
		versions.put("CALLDATACOPY", new HashMap<String, Integer>());
		versions.put("CODESIZE", new HashMap<String, Integer>());
		versions.put("CODECOPY", new HashMap<String, Integer>());
		versions.put("GASPRICE", new HashMap<String, Integer>());
		versions.put("EXTCODESIZE", new HashMap<String, Integer>());
		versions.put("EXTCODECOPY", new HashMap<String, Integer>());
		versions.put("BLOCKHASH", new HashMap<String, Integer>());
		versions.put("COINBASE", new HashMap<String, Integer>());
		versions.put("TIMESTAMP", new HashMap<String, Integer>());
		versions.put("NUMBER", new HashMap<String, Integer>());
		versions.put("DIFFICULTY", new HashMap<String, Integer>());
		versions.put("GASLIMIT", new HashMap<String, Integer>());
		versions.put("POP", new HashMap<String, Integer>());
		versions.put("MLOAD", new HashMap<String, Integer>());
		versions.put("MSTORE", new HashMap<String, Integer>());
		versions.put("MSTORE8", new HashMap<String, Integer>());
		versions.put("SLOAD", new HashMap<String, Integer>());
		versions.put("SSTORE", new HashMap<String, Integer>());
		versions.put("JUMP", new HashMap<String, Integer>());
		versions.put("JUMPI", new HashMap<String, Integer>());
		versions.put("PC", new HashMap<String, Integer>());
		versions.put("MSIZE", new HashMap<String, Integer>());
		versions.put("GAS", new HashMap<String, Integer>());
		versions.put("JUMPDEST", new HashMap<String, Integer>());
		versions.put("PUSH1", new HashMap<String, Integer>());
		versions.put("PUSH2", new HashMap<String, Integer>());
		versions.put("PUSH3", new HashMap<String, Integer>());
		versions.put("PUSH4", new HashMap<String, Integer>());
		versions.put("PUSH5", new HashMap<String, Integer>());
		versions.put("PUSH6", new HashMap<String, Integer>());
		versions.put("PUSH7", new HashMap<String, Integer>());
		versions.put("PUSH8", new HashMap<String, Integer>());
		versions.put("PUSH9", new HashMap<String, Integer>());
		versions.put("PUSH10", new HashMap<String, Integer>());
		versions.put("PUSH11", new HashMap<String, Integer>());
		versions.put("PUSH12", new HashMap<String, Integer>());
		versions.put("PUSH13", new HashMap<String, Integer>());
		versions.put("PUSH14", new HashMap<String, Integer>());
		versions.put("PUSH15", new HashMap<String, Integer>());
		versions.put("PUSH16", new HashMap<String, Integer>());
		versions.put("PUSH17", new HashMap<String, Integer>());
		versions.put("PUSH18", new HashMap<String, Integer>());
		versions.put("PUSH19", new HashMap<String, Integer>());
		versions.put("PUSH20", new HashMap<String, Integer>());
		versions.put("PUSH21", new HashMap<String, Integer>());
		versions.put("PUSH22", new HashMap<String, Integer>());
		versions.put("PUSH23", new HashMap<String, Integer>());
		versions.put("PUSH24", new HashMap<String, Integer>());
		versions.put("PUSH25", new HashMap<String, Integer>());
		versions.put("PUSH26", new HashMap<String, Integer>());
		versions.put("PUSH27", new HashMap<String, Integer>());
		versions.put("PUSH28", new HashMap<String, Integer>());
		versions.put("PUSH29", new HashMap<String, Integer>());
		versions.put("PUSH30", new HashMap<String, Integer>());
		versions.put("PUSH31", new HashMap<String, Integer>());
		versions.put("PUSH32", new HashMap<String, Integer>());
		versions.put("DUP1", new HashMap<String, Integer>());
		versions.put("DUP2", new HashMap<String, Integer>());
		versions.put("DUP3", new HashMap<String, Integer>());
		versions.put("DUP4", new HashMap<String, Integer>());
		versions.put("DUP5", new HashMap<String, Integer>());
		versions.put("DUP6", new HashMap<String, Integer>());
		versions.put("DUP7", new HashMap<String, Integer>());
		versions.put("DUP8", new HashMap<String, Integer>());
		versions.put("DUP9", new HashMap<String, Integer>());
		versions.put("DUP10", new HashMap<String, Integer>());
		versions.put("DUP11", new HashMap<String, Integer>());
		versions.put("DUP12", new HashMap<String, Integer>());
		versions.put("DUP13", new HashMap<String, Integer>());
		versions.put("DUP14", new HashMap<String, Integer>());
		versions.put("DUP15", new HashMap<String, Integer>());
		versions.put("DUP16", new HashMap<String, Integer>());
		versions.put("SWAP1", new HashMap<String, Integer>());
		versions.put("SWAP2", new HashMap<String, Integer>());
		versions.put("SWAP3", new HashMap<String, Integer>());
		versions.put("SWAP4", new HashMap<String, Integer>());
		versions.put("SWAP5", new HashMap<String, Integer>());
		versions.put("SWAP6", new HashMap<String, Integer>());
		versions.put("SWAP7", new HashMap<String, Integer>());
		versions.put("SWAP8", new HashMap<String, Integer>());
		versions.put("SWAP9", new HashMap<String, Integer>());
		versions.put("SWAP10", new HashMap<String, Integer>());
		versions.put("SWAP11", new HashMap<String, Integer>());
		versions.put("SWAP12", new HashMap<String, Integer>());
		versions.put("SWAP13", new HashMap<String, Integer>());
		versions.put("SWAP14", new HashMap<String, Integer>());
		versions.put("SWAP15", new HashMap<String, Integer>());
		versions.put("SWAP16", new HashMap<String, Integer>());
		versions.put("LOG0", new HashMap<String, Integer>());
		versions.put("LOG1", new HashMap<String, Integer>());
		versions.put("LOG2", new HashMap<String, Integer>());
		versions.put("LOG3", new HashMap<String, Integer>());
		versions.put("LOG4", new HashMap<String, Integer>());
		versions.put("CREATE", new HashMap<String, Integer>());
		versions.put("CALL", new HashMap<String, Integer>());
		versions.put("CALLCODE", new HashMap<String, Integer>());
		versions.put("RETURN", new HashMap<String, Integer>());
		versions.put("DELEGATECALL", new HashMap<String, Integer>());
		versions.put("STATICCAL", new HashMap<String, Integer>());
		versions.put("REVERT", new HashMap<String, Integer>());
		versions.put("INVALID", new HashMap<String, Integer>());
		versions.put("SELFDESTRUCT", new HashMap<String, Integer>());	
		
		// Inizializza a 0 il counter per ogni versione
		versions.forEach ((opcode, version) -> {
			  Iterator<String> j = v.iterator();
			  while(j.hasNext()) {
				  String app = j.next();
				  versions.get(opcode).put(app, 0);
			  }
		 });
	}
	
	/**
	 * Ottiene il numero corrente di blocchi presenti nella Blockchain
	 * @return line - numbero di blocchi
	 * @throws Exception
	 */
	public static String getBlocksNumber() throws Exception {
		URL website = new URL("https://etherscan.io/blocks");
        URLConnection connection = website.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        connection.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        
        String[] splited = null;
        String inputLine;
        String line = null;
        boolean flag = false;
        int i = 0;
        
        while ((inputLine = in.readLine()) != null) {
            splited = inputLine.split("\\s+");
            for (String part : splited) {          	
            	if (part.contains("Showing") == true) {
            		flag = true;
            		i = 0;
            	}
            	i++;
            	if(i == 3 && flag == true) {
            		line = part.substring(2, part.length());  
            		break;
            	}
            }	
        }
        return line;
	}
	
	/**
	 * Ottiene gli indirizzi dei contratti e li memorizza su file
	 * @param url - url pagine contenenti i contratti
	 * @throws Exception
	 */
    public ArrayList<String> getAddresses(String url) throws Exception {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        connection.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        ArrayList<String> addresses = new ArrayList<String>();
        String[] splited = null;
        String inputLine;
    	boolean flag = false;
    	int i = 0;
    	String line = new String();

        while ((inputLine = in.readLine()) != null) {
            splited = inputLine.split("\\s+");
            for (String part : splited) {
            	if (part.contains("title='Contract'") == true) {
            		flag = true;
            		i = 0;
            	}
            	i++;
            	if(i == 4 && flag == true) {
            		line = part.substring(15, 57);
            		addresses.add(line);
            		i = 0;
            		flag=false;
            	}
            }
        }           
        in.close();
        return addresses;
    }
    
    /**
     * Verifica se il contratto corrispondente all'indirizzo specificato è stato verificato.
     * @param url		indirizzo del contratto
     * @return boolean
     * @throws Exception
     */
    public boolean isVerified(String url) throws Exception {
        boolean verified = false;
        
    	try {
       	 	URL website = new URL(url);
            URLConnection connection = website.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
           
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
               	if (inputLine.contains("Contract Source Code Verified") == true) {
               		verified = true;
               	}
            }  
            in.close();
       	}
       	catch(Exception e) {
       		System.out.println("Errore nel contratto "+url.substring(29, url.lastIndexOf("#"))+". Impossibile recuperare la versione del compilatore.");
       	}
		return verified;
    }
    
    /**
     * Ottiene la versione del compilatore Solidity utilizzata e la memorizza su file
     * @param url - url pagine contenenti i contratti
     * @throws Exception
     */
    public String getCompilerVersion(String url) throws Exception {
        String version = new String();
        
    	try {
    	 URL website = new URL(url);
         URLConnection connection = website.openConnection();
         connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
         connection.connect();
         BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        
         String inputLine = new String();
         boolean compiler = false;
         int index = 0;
         int i = 0;
         while ((inputLine = in.readLine()) != null) {
            	if (inputLine.contains("Version") == true) {
            		compiler=true;
            		i = 0;
            	}
            	i++;
            	if(compiler == true  && i==4) {
            		if(inputLine.contains("v") == true && inputLine.contains("+") == true) {            			
            			index = inputLine.lastIndexOf("+");
            			version = inputLine.substring(1, index);
            			i = 0;
            			compiler = false;
            		}
            	}
         }  
         in.close();
    	 }
    	 catch(Exception e) {
    		 System.out.println("Errore nel contratto "+url.substring(29, url.lastIndexOf("#"))+". Impossibile recuperare la versione del compilatore.");
    	 }
		 return version;
    }
    
    /**
     * Ottiene gli Opcode tramite il codice sorgente di ogni pagina dei contratti
     * e li memorizza su file
     * @param url - url del contratto
     * @throws Exception
     */
    public void getOpcode(String url) throws Exception {
   	 URL website = new URL(url);
   	 
   	 LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
   	 java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
   	 java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
   	 
   	 WebClient webClient = new WebClient();
   	 webClient.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.waitForBackgroundJavaScript(10000);
        
        webClient.setAlertHandler(new AlertHandler() {
            public void handleAlert(Page page, String message) {
                throw new AssertionError();
            }
        });
        
       try {
    	     /* Eseguo il codice javascript relativo all'evento onclick(),
    	      * per accedere alla versione della pagina contenente gli opcodes
    	      */
	         HtmlPage currentPage = webClient.getPage(website);
	         HtmlPage page;
	    	 String opcodeId = new String();
	         if(currentPage.getElementById("btnConvert3") != null) {
	        	 HtmlAnchor element = (HtmlAnchor) currentPage.getElementById("btnConvert3");
	        	 String clickAttr = element.getOnClickAttribute();
		         ScriptResult scriptResult = currentPage.executeJavaScript(clickAttr);
		         page =  (HtmlPage) scriptResult.getNewPage();
		         opcodeId = "verifiedbytecode2";
	         }
	         else {
	        	 HtmlButton element = (HtmlButton) currentPage.getElementById("ContentPlaceHolder1_btnconvert222");
	        	 String clickAttr = element.getOnClickAttribute();
		         ScriptResult scriptResult = currentPage.executeJavaScript(clickAttr);
		         page = (HtmlPage) scriptResult.getNewPage();
		         opcodeId = "dividcode";
	         }
	
	         String prova = page.asXml();
	         Reader inputString = new StringReader(prova);
	         BufferedReader in = new BufferedReader(inputString);
	         
	   		 String inputLine;
	         Element app;
	         Document doc;
	         boolean flag = false;
	         int i = 0;	         
	         int startIndex = 0;
	         String opcode = new String();
	         
	         boolean verified = this.isVerified(url);
	         String version = this.getCompilerVersion(url);
	        
	         while ((inputLine = in.readLine()) != null) {
	        		doc = Jsoup.parse(inputLine);
	        		if((app = doc.getElementById(opcodeId)) != null){
	        			flag = true;
	        			i = 0;
	        		}
	        		if(inputLine.contains("fa fa-database") == true || inputLine.contains("readContract") == true || inputLine.contains("fa fa-plus-square") == true) {
	        			flag = false;
	        		}
	        		i++;
	        		if(flag == true && i > 1) {
	        			if(inputLine.contains("<br/>") == false && inputLine.contains("</div>") == false && inputLine.contains("</pre>") == false && inputLine.contains("wordwrap") == false) {	  
	        				if(verified) startIndex = 24;
	        				else startIndex = 22;
	        				if(inputLine.contains("PUSH")) {
	        					opcode = inputLine.substring(startIndex, inputLine.lastIndexOf(" "));
	        				}
	        				else if(!inputLine.contains("Unknown Opcode")){	
	        					if(inputLine.length() > startIndex)
	        					opcode = inputLine.substring(startIndex, inputLine.length());	        					
	        				}
	        				/* Se il contratto risulta verificato incremento il relativo counter */
	        				if(verified) {	        					
	        					if(opcodes.containsKey(opcode)) {
		        					int array[] = opcodes.get(opcode);
		        					array[1] += 1;
		        					opcodes.replace(opcode, array);
	        					}
	        				}		/* Altrimenti incremento quello relativo ai non verificati */
	        				else {
	        					if(opcodes.containsKey(opcode)) {
		        					int array[] = opcodes.get(opcode);
		        					array[0] += 1;
		        					opcodes.replace(opcode, array);
	        					}
	        				}	        				
	        				if(!version.isEmpty()) {
	        					/* Incremento il counter dell'opcode per la versione specificata */
		        				if(versions.containsKey(opcode)) {
			        				HashMap<String, Integer> map = versions.get(opcode);
			        				if(version.contains("-")) version = version.substring(0, version.lastIndexOf("-"));
			        				if(map.containsKey(version)) {
				        				int count = map.get(version);
				        				count++;
				        				map.replace(version, count);
				        				versions.replace(opcode, map);	 
			        				}
			        				/* Se il contratto è stato verificato ed è stato prodotto con un compilatore solidity incremento
			        				 * il relativo counter
			        				 */
			        				if(verified && v.contains(version)) {
			        					int array[] = opcodes.get(opcode);
			        					array[2] += 1;
			        					opcodes.replace(opcode, array);			        					
			        				}
			        				/* Altrimenti incremento il counter per contratti prodotti da altri linguaggi */
			        				else if(verified && !v.contains(version)) {
			        					int array[] = opcodes.get(opcode);
			        					array[3] += 1;
			        					opcodes.replace(opcode, array);				        					
			        				}
		        				}
	        				}
	        			}
	        		}
	         }
	         in.close();
	         webClient.close();
       }
       catch (Exception e) {
       	System.out.println("Errore nel contratto "+url.substring(29, url.lastIndexOf("#"))+". Impossibile recuperare gli Opcodes.");
       	e.printStackTrace();
       }
   }

    /**
     * Memorizza i risultati della statistica degli opcodes su file
     * @throws Exception
     */
    public void writeOpcodesResults(String filename) throws Exception {
        FileOutputStream output = new FileOutputStream(filename);
		PrintStream write = new PrintStream(output);
		write.println("Opcodes results: ");
		
		opcodes.forEach ((opcode, counter) -> {
			  int array[] = opcodes.get(opcode);
			  write.println("Opcode: "+opcode+" numerosità da non verificati: "+array[0]+" numerosità da verificati: "+array[1]+" numerosità da verificati con solidity: "+array[2]+" numerosità da verificati non da solidity: "+array[3]);
		 });
		write.close();
    }
    
    /**
     * Memorizza i risultati della statistica degli opcodes per ogni versione su file
     * @throws Exception
     */
    public void writeVersionsResults(String filename) throws Exception {
        FileOutputStream output = new FileOutputStream(filename);
		PrintStream write = new PrintStream(output);
		write.println("Versions results: ");
		
		versions.forEach ((opcode, version) -> {
			  Iterator<String> j = v.iterator();
			  while(j.hasNext()) {
				  String app = j.next();
				  int count = versions.get(opcode).get(app);
				  write.println("Opcode: "+opcode+" versione: "+app+" numerosità utilizzo:"+count);
			  }
		 });
		write.close();
    }
    
    
    public static void main(String[] args) throws Exception {
    	BlockchainParser bp = new BlockchainParser();
    	ArrayList<String> addresses = new ArrayList<String>();
    	bp.createOpcodesMap();
    	bp.insertVersions();
    	bp.createVersionsMap();
    	int index = Integer.parseInt(getBlocksNumber());
    	System.out.println("Blocco corrente: "+index);
    	System.out.println("Scansione contratti in corso..");
    	for(int i = index; i >= 0; i--) {    		
    		addresses = bp.getAddresses("http://etherscan.io/txs?block="+i);
    		Iterator<String> j = addresses.iterator();
    		while(j.hasNext()) {
    			bp.getOpcode("https://etherscan.io/address/"+j.next()+"#code");
    		}
    		// Decommentare solo in caso di http error 403
    		//TimeUnit.SECONDS.sleep(1);
    	}
    	System.out.println("Memorizzazione su file dei risultati.");
    	bp.writeOpcodesResults("OpcodesResults.txt");
    	bp.writeVersionsResults("VersionsResults.txt");
    }
}

