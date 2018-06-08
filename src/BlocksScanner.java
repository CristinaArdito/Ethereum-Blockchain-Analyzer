import java.net.*;
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

public class BlocksScanner {

	// Lista di OpCode : http://solidity.readthedocs.io/en/develop/assembly.html
	private int stop;
	private int add;
	private int mul;
	private int sub;
	private int div;
	private int sdiv;
	private int mod;
	private int smod;
	private int exp;
	private int not;
	private int if1;
	private int lt;
	private int gt;
	private int slt;
	private int sgt;
	private int eq;
	private int iszero;
	private int and;
	private int or;
	private int xor;
	private int byte1;
	private int addmod;
	private int mulmod;
	private int signextend;
	private int keccak256;
	private int sha3;
	private int jump;
	private int jumpi;
	private int jumpdest;
	private int pop;
	private int mload;
	private int mstore;
	private int mstore8;
	private int sload;
	private int sstore;
	private int msize;
	private int gas;
	private int address;
	private int balance;
	private int caller;
	private int callvalue;
	private int calldataload;
	private int calldatasize;
	private int calldatacopy;
	private int codesize;
	private int codecopy;
	private int extcodesize;
	private int extcodecopy;
	private int returndatasize;
	private int returndatacopy;
	private int create;
	private int create2;
	private int call;
	private int callcode;
	private int delegatecall;
	private int staticcall;
	private int return1;
	private int revert;
	private int selfdestruct;
	private int invalid;
	private int log0;
	private int log1;
	private int log2;
	private int log3;
	private int log4;
	private int origin;
	private int gasprice;
	private int blockhash;
	private int coinbase;
	private int timestamp;
	private int number;
	private int difficulty;
	private int gaslimit;
	private int pc;
	private int push1;
	private int push2;
	private int push3;
	private int push4;
	private int push5;
	private int push6;
	private int push7;
	private int push8;
	private int push9;
	private int push10;
	private int push11;
	private int push12;
	private int push13;
	private int push14;
	private int push15;
	private int push16;
	private int push17;
	private int push18;
	private int push19;
	private int push20;
	private int push21;
	private int push22;
	private int push23;
	private int push24;
	private int push25;
	private int push26;
	private int push27;
	private int push28;
	private int push29;
	private int push30;
	private int push31;
	private int push32;
	private int dup1;
	private int dup2;
	private int dup3;
	private int dup4;
	private int dup5;
	private int dup6;
	private int dup7;
	private int dup8;
	private int dup9;
	private int dup10;
	private int dup11;
	private int dup12;
	private int dup13;
	private int dup14;
	private int dup15;
	private int dup16;
	private int swap1;
	private int swap2;
	private int swap3;
	private int swap4;
	private int swap5;
	private int swap6;
	private int swap7;
	private int swap8;
	private int swap9;
	private int swap10;
	private int swap11;
	private int swap12;
	private int swap13;
	private int swap14;
	private int swap15;
	private int swap16;
	
	
	/**
	 * Ottiene il numero totale delle pagine contenenti i contratti
	 * @return line - numero delle pagine 
	 * @throws Exception
	 */
	public static String getPagesBlocks() throws Exception {
		URL website = new URL("https://etherscan.io/blocks");
        URLConnection connection = website.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        connection.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        
        String[] splited = null;
        String inputLine;
        String line = null;
        
        while ((inputLine = in.readLine()) != null) {
            splited = inputLine.split("\\s+");
            for (String part : splited) {
            	if (part.contains("href=\"blocks?p=") == true) {
            		int index = part.lastIndexOf("\"");
            		line = part.substring(15, index);           		
            	}
            }	
        }
        return line;
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
    public void getAddresses(String url) throws Exception {
    	boolean flag = false;
    	int i = 0;
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        connection.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        FileOutputStream output = new FileOutputStream("AddressesBlockchain.txt", true);
		PrintStream write = new PrintStream(output);
		
        String[] splited = null;
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            splited = inputLine.split("\\s+");
            for (String part : splited) {
            	if (part.contains("title='Contract'") == true) {
            		flag = true;
            		i = 0;
            	}
            	i++;
            	if(i == 4 && flag == true) {
            		String line = part.substring(15, 57);
            		write.println(line);
            		i = 0;
            		flag=false;
            	}
            }
        }           
        in.close();
        write.close();
    }
    
    /**
     * Ottiene la versione del compilatore Solidity utilizzata e la memorizza su file
     * @param url - url pagine contenenti i contratti
     * @throws Exception
     */
    public void getCompilerVersion(String url) throws Exception {
    	 URL website = new URL(url);
         URLConnection connection = website.openConnection();
         connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
         connection.connect();
         BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        
        FileOutputStream output = new FileOutputStream("CompilerVersion.txt", true);
		PrintStream write = new PrintStream(output);
        String inputLine;
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
            			write.println("Compiler Solidity version: "+ inputLine.substring(1, index));
            			i = 0;
            			compiler = false;
            		}
            	}
        }  
        in.close();
        write.close();
		
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
    	 webClient.getCache().setMaxSize(0);
         webClient.getOptions().setJavaScriptEnabled(true);
         webClient.waitForBackgroundJavaScript(10000);
         
         webClient.setAlertHandler(new AlertHandler() {
             public void handleAlert(Page page, String message) {
                 throw new AssertionError();
             }
         });
         
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
         
         FileOutputStream output = new FileOutputStream("OpcodeBlockchain.txt", true);
 		 PrintStream write = new PrintStream(output);
 		 
         write.println("Contract address: " + url.substring(29, url.lastIndexOf("#")));
   		 String inputLine;
         Element app;
         Document doc;
         boolean flag = false;
         int i = 0;
        
         while ((inputLine = in.readLine()) != null) {
        		doc = Jsoup.parse(inputLine);
        		if((app = doc.getElementById(opcodeId)) != null){
        			flag = true;
        			i = 0;
        		}
        		if(inputLine.contains("fa fa-database") == true || inputLine.contains("readContract") == true) {
        			flag = false;
        		}
        		i++;
        		if(flag == true && i > 1) {
        			if(inputLine.contains("<br/>") == false && inputLine.contains("</div>") == false && inputLine.contains("</pre>") == false && inputLine.contains("wordwrap") == false) {
        				write.println(inputLine);
        			}
        		}

         }  
         write.println();
         in.close();
         webClient.close();
         write.close();
    }

    /**
     * Effetua la statistica degli opcode dei contratti
     * @throws Exception
     */
    public void statistics() throws Exception {
    	File file = new File("OpcodeBlockchain.txt");
    	BufferedReader br = new BufferedReader(new FileReader(file));
    	String line;
    	while ((line = br.readLine()) != null) {
       		if(line.contains("STOP") == true) {
    			this.stop++;
    		}
    		if (line.contains("ADD") == true) {
				this.add++;
    		}	
    		if (line.contains("MUL") == true) {
				this.mul++;
    		}	
    		if (line.contains("SUB") == true) {
				this.sub++;
    		}	
    		if (line.contains("DIV") == true) {
				this.div++;
    		}	
    		if (line.contains("SDIV") == true) {
				this.sdiv++;
    		}	
    		if (line.contains("MOD") == true) {
				this.mod++;
    		}	
    		if (line.contains("SMOD") == true) {
				this.smod++;
    		}	
    		if (line.contains("EXP") == true) {
				this.exp++;
    		}
    		if (line.contains("NOT") == true) {
				this.not++;
    		}	
    		if (line.contains(" IF ") == true) {
				this.if1++;
    		}	
    		if (line.contains("LT") == true) {
				this.lt++;
    		}	
    		if (line.contains("GT") == true) {
				this.gt++;
    		}
    		if (line.contains("SLT") == true) {
				this.slt++;
    		}
    		if (line.contains("SGT") == true) {
				this.sgt++;
    		}	
    		if (line.contains("EQ") == true) {
				this.eq++;
    		}	
    		if (line.contains("ISZERO") == true) {
				this.iszero++;
    		}	
    		if (line.contains("AND") == true) {
				this.and++;
    		}
    		if (line.contains("OR") == true) {
				this.or++;
    		}
    		if (line.contains("XOR") == true) {
				this.xor++;
    		}
    		if (line.contains("BYTE") == true) {
				this.byte1++;
    		}	
    		if (line.contains("ADDMOD") == true) {
				this.addmod++;
    		}	
    		if (line.contains("MULMOD") == true) {
				this.mulmod++;
    		}	
    		if (line.contains("SIGNEXTEND") == true) {
				this.signextend++;
    		}
    		if (line.contains("KECCAK256") == true) {
				this.keccak256++;
    		}	
    		if (line.contains("SHA3") == true) {
				this.sha3++;
    		}	
    		if (line.contains("JUMP") == true) {
				this.jump++;
    		}	
    		if (line.contains("JUMPI") == true) {
				this.jumpi++;
    		}
    		if (line.contains("POP") == true) {
				this.pop++;
    		}
    		if (line.contains("MLOAD") == true) {
				this.mload++;
    		}		
    		if (line.contains("MSTORE") == true) {
				this.mstore++;
    		}	
    		if (line.contains("MSTORE8") == true) {
				this.mstore8++;
    		}
    		if (line.contains("SLOAD") == true) {
				this.sload++;
    		}
    		if (line.contains("SSTORE") == true) {
				this.sstore++;
    		}
    		if (line.contains("MSIZE") == true) {
				this.msize++;
    		}	
    		if (line.contains("GAS") == true) {
				this.gas++;
    		}	
    		if (line.contains("ADDRESS") == true) {
				this.address++;
    		}	
    		if (line.contains("BALANCE") == true) {
				this.balance++;
    		}
    		if (line.contains("CALLER") == true) {
				this.caller++;
    		}
    		if (line.contains("CALLVALUE") == true) {
				this.callvalue++;
    		}
    		if (line.contains("CALLDATALOAD") == true) {
				this.calldataload++;
    		}	
    		if (line.contains("CALLDATASIZE") == true) {
				this.calldatasize++;
    		}	
    		if (line.contains("CALLDATACOPY") == true) {
				this.calldatacopy++;
    		}	
    		if (line.contains("CODESIZE") == true) {
				this.codesize++;
    		}
    		if (line.contains("CODECOPY") == true) {
				this.codecopy++;
    		}
    		if (line.contains("EXTCODESIZE") == true) {
				this.extcodesize++;
    		}
    		if (line.contains("EXTCODECOPY") == true) {
				this.extcodecopy++;
    		}
    		if (line.contains("RETURNDATASIZE") == true) {
				this.returndatasize++;
    		}	
    		if (line.contains("RETURNDATACOPY") == true) {
				this.returndatacopy++;
    		}	
    		if (line.contains("CREATE") == true) {
				this.create++;
    		}	
    		if (line.contains("CREATE2") == true) {
				this.create2++;
    		}
    		if (line.contains("CALL") == true) {
				this.call++;
    		}
    		if (line.contains("CALLCODE") == true) {
				this.callcode++;
    		}
    		if (line.contains("DELEGATECALL") == true) {
				this.delegatecall++;
    		}
    		if (line.contains("STATICCALL") == true) {
				this.staticcall++;
    		}
    		if (line.contains("RETURN") == true) {
				this.return1++;
    		}	
    		if (line.contains("REVERT") == true) {
				this.revert++;
    		}	
    		if (line.contains("SELFDESTRUCT") == true) {
				this.selfdestruct++;
    		}	
    		if (line.contains("INVALID") == true) {
				this.invalid++;
    		}
    		if (line.contains("LOG0") == true) {
				this.log0++;
    		}	
    		if (line.contains("LOG1") == true) {
				this.log1++;
    		}
    		if (line.contains("LOG2") == true) {
				this.log2++;
    		}
    		if (line.contains("LOG3") == true) {
				this.log3++;
    		}
    		if (line.contains("LOG4") == true) {
				this.log4++;
    		}
    		if (line.contains("ORIGIN") == true) {
				this.origin++;
    		}
    		if (line.contains("GASPRICE") == true) {
				this.gasprice++;
    		}	
    		if (line.contains("BLOCKHASH") == true) {
				this.blockhash++;
    		}	
    		if (line.contains("COINBASE") == true) {
				this.coinbase++;
    		}	
    		if (line.contains("TIMESTAMP") == true) {
				this.timestamp++;
    		}
    		if (line.contains("NUMBER") == true) {
				this.number++;
    		}	
    		if (line.contains("DIFFICULTY") == true) {
				this.difficulty++;
    		}	
    		if (line.contains("GASLIMIT") == true) {
				this.gaslimit++;
    		}
    		if(line.contains("PC") == true) {
    			this.pc++;
    		}
    		if(line.contains("JUMPDEST") == true) {
    			this.jumpdest++;
    		}
    		if(line.contains("PUSH1") == true) {
    			this.push1++;
    		}
    		if(line.contains("PUSH2") == true) {
    			this.push2++;
    		}
    		if(line.contains("PUSH3") == true) {
    			this.push3++;
    		}
    		if(line.contains("PUSH4") == true) {
    			this.push4++;
    		}
    		if(line.contains("PUSH5") == true) {
    			this.push5++;
    		}
    		if(line.contains("PUSH6") == true) {
    			this.push6++;
    		}
    		if(line.contains("PUSH7") == true) {
    			this.push7++;
    		}
    		if(line.contains("PUSH8") == true) {
    			this.push8++;
    		}
    		if(line.contains("PUSH9") == true) {
    			this.push9++;
    		}
    		if(line.contains("PUSH10") == true) {
    			this.push10++;
    		}
    		if(line.contains("PUSH11") == true) {
    			this.push11++;
    		}
    		if(line.contains("PUSH12") == true) {
    			this.push12++;
    		}
    		if(line.contains("PUSH13") == true) {
    			this.push13++;
    		}
    		if(line.contains("PUSH14") == true) {
    			this.push14++;
    		}
    		if(line.contains("PUSH15") == true) {
    			this.push15++;
    		}
    		if(line.contains("PUSH16") == true) {
    			this.push17++;
    		}
    		if(line.contains("PUSH17") == true) {
    			this.push17++;
    		}
    		if(line.contains("PUSH19") == true) {
    			this.push19++;
    		}
    		if(line.contains("PUSH18") == true) {
    			this.push18++;
    		}
    		if(line.contains("PUSH20") == true) {
    			this.push20++;
    		}
    		if(line.contains("PUSH21") == true) {
    			this.push21++;
    		}
    		if(line.contains("PUSH22") == true) {
    			this.push22++;
    		}
    		if(line.contains("PUSH23") == true) {
    			this.push23++;
    		}
    		if(line.contains("PUSH24") == true) {
    			this.push24++;
    		}
    		if(line.contains("PUSH25") == true) {
    			this.push25++;
    		}
    		if(line.contains("PUSH26") == true) {
    			this.push26++;
    		}
    		if(line.contains("PUSH27") == true) {
    			this.push27++;
    		}
    		if(line.contains("PUSH28") == true) {
    			this.push28++;
    		}
    		if(line.contains("PUSH29") == true) {
    			this.push29++;
    		}
    		if(line.contains("PUSH30") == true) {
    			this.push30++;
    		}
    		if(line.contains("PUSH31") == true) {
    			this.push31++;
    		}
    		if(line.contains("PUSH32") == true) {
    			this.push32++;
    		}
    		if(line.contains("DUP1") == true) {
    			this.dup1++;
    		}
    		if(line.contains("DUP2") == true) {
    			this.dup2++;
    		}
    		if(line.contains("DUP3") == true) {
    			this.dup3++;
    		}
    		if(line.contains("DUP4") == true) {
    			this.dup4++;
    		}
    		if(line.contains("DUP5") == true) {
    			this.dup5++;
    		}
    		if(line.contains("DUP6") == true) {
    			this.dup6++;
    		}
    		if(line.contains("DUP7") == true) {
    			this.dup7++;
    		}
    		if(line.contains("DUP8") == true) {
    			this.dup8++;
    		}
    		if(line.contains("DUP9") == true) {
    			this.dup9++;
    		}
    		if(line.contains("DUP10") == true) {
    			this.dup10++;
    		}
    		if(line.contains("DUP11") == true) {
    			this.dup11++;
    		}
    		if(line.contains("DUP12") == true) {
    			this.dup12++;
    		}
    		if(line.contains("DUP13") == true) {
    			this.dup13++;
    		}
    		if(line.contains("DUP14") == true) {
    			this.dup14++;
    		}
    		if(line.contains("DUP15") == true) {
    			this.dup15++;
    		}
    		if(line.contains("DUP16") == true) {
    			this.dup16++;
    		}
    		if(line.contains("SWAP1") == true) {
    			this.swap1++;
    		}
    		if(line.contains("SWAP2") == true) {
    			this.swap2++;
    		}
    		if(line.contains("SWAP3") == true) {
    			this.swap3++;
    		}
    		if(line.contains("SWAP4") == true) {
    			this.swap4++;
    		}
    		if(line.contains("SWAP5") == true) {
    			this.swap5++;
    		}
    		if(line.contains("SWAP6") == true) {
    			this.swap6++;
    		}
    		if(line.contains("SWAP7") == true) {
    			this.swap7++;
    		}
    		if(line.contains("SWAP8") == true) {
    			this.swap8++;
    		}
    		if(line.contains("SWAP9") == true) {
    			this.swap9++;
    		}
    		if(line.contains("SWAP10") == true) {
    			this.swap10++;
    		}
    		if(line.contains("SWAP11") == true) {
    			this.swap11++;
    		}
    		if(line.contains("SWAP12") == true) {
    			this.swap12++;
    		}
    		if(line.contains("SWAP13") == true) {
    			this.swap13++;
    		}
    		if(line.contains("SWAP14") == true) {
    			this.swap14++;
    		}
    		if(line.contains("SWAP15") == true) {
    			this.swap15++;
    		}
    		if(line.contains("SWAP16") == true) {
    			this.swap16++;
    		}
    		
    	}
    	br.close();
    }
    
    /**
     * Memorizza i risultati della statistica su file
     * @throws Exception
     */
    public void writeResults() throws Exception {
        FileOutputStream output = new FileOutputStream("BlockchainResults.txt", true);
		PrintStream write = new PrintStream(output);
		write.println("Opcode results: ");
		write.println("add: "+ this.add);
		write.println("mul: "+ this.mul);
		write.println("sub: "+ this.sub);
		write.println("div: "+ this.div);
		write.println("sdiv: "+ this.sdiv);
		write.println("mod: "+ this.mod);
		write.println("smod: "+ this.smod);
		write.println("exp: "+ this.exp);
		write.println("not: "+ this.not);
		write.println("if: "+ this.if1);
		write.println("lt: "+ this.lt);
		write.println("gt: "+ this.gt);
		write.println("slt: "+ this.slt);
		write.println("sgt: "+ this.sgt);
		write.println("eq: "+ this.eq);
		write.println("iszero: "+ this.iszero);
		write.println("and: "+ this.and);
		write.println("or: "+ this.or);
		write.println("xor: "+ this.xor);
		write.println("byte: "+ this.byte1);
		write.println("addmod: "+ this.addmod);
		write.println("mulmod: "+ this.mulmod);
		write.println("signextend: "+ this.signextend);
		write.println("keccak256: "+ this.keccak256);
		write.println("sha3: "+ this.sha3);
		write.println("jump: "+ this.jump);
		write.println("jumpi: "+ this.jumpi);	
		write.println("pop: "+ this.pop);	
		write.println("mload: "+ this.mload);	
		write.println("mstore: "+ this.mstore);	
		write.println("mstore8: "+ this.mstore8);	
		write.println("sload: "+ this.sload);	
		write.println("sstore: "+ this.sstore);	
		write.println("msize: "+ this.msize);	
		write.println("gas: "+ this.gas);	
		write.println("address: "+ this.address);	
		write.println("balance: "+ this.balance);	
		write.println("caller: "+ this.caller);	
		write.println("callvalue: "+ this.callvalue);	
		write.println("calldataload: "+ this.calldataload);	
		write.println("calldatasize: "+ this.calldatasize);	
		write.println("calldatacopy: "+ this.calldatacopy);	
		write.println("codesize: "+ this.codesize);	
		write.println("codecopy: "+ this.codecopy);	
		write.println("extcodesize: "+ this.extcodesize);	
		write.println("extcodecopy: "+ this.extcodecopy);	
		write.println("returndatasize: "+ this.returndatasize);	
		write.println("returndatacopy: "+ this.returndatacopy);	
		write.println("create: "+ this.create);	
		write.println("create2: "+ this.create2);	
		write.println("call: "+ this.call);	
		write.println("callcode: "+ this.callcode);	
		write.println("delegatecall: "+ this.delegatecall);	
		write.println("staticcall: "+ this.staticcall);	
		write.println("return: "+ this.return1);	
		write.println("revert: "+ this.revert);	
		write.println("selfdestruct: "+ this.selfdestruct);	
		write.println("invalid: "+ this.invalid);	
		write.println("log0: "+ this.log0);	
		write.println("log1: "+ this.log1);	
		write.println("log2: "+ this.log2);	
		write.println("log3: "+ this.log3);	
		write.println("log4: "+ this.log4);	
		write.println("origin: "+ this.origin);
		write.println("gasprice: "+ this.gasprice);	
		write.println("blockhash: "+ this.blockhash);	
		write.println("coinbase: "+ this.coinbase);	
		write.println("timestamp: "+ this.timestamp);	
		write.println("number: "+ this.number);	
		write.println("difficulty: "+ this.difficulty);	
		write.println("gaslimit: "+ this.gaslimit);	
		write.println("stop: "+ this.stop);
		write.println("pc: "+this.pc);
		write.println("jumpdest: "+this.jumpdest);
		write.println("push1: "+this.push1);
		write.println("push2: "+this.push2);
		write.println("push3: "+this.push3);
		write.println("push4: "+this.push4);
		write.println("push5: "+this.push5);
		write.println("push6: "+this.push6);
		write.println("push7: "+this.push7);
		write.println("push8: "+this.push8);
		write.println("push9: "+this.push9);
		write.println("push10: "+this.push10);
		write.println("push11: "+this.push11);
		write.println("push12: "+this.push12);
		write.println("push13: "+this.push13);
		write.println("push14: "+this.push14);
		write.println("push15: "+this.push15);
		write.println("push16: "+this.push16);
		write.println("push17: "+this.push17);
		write.println("push18: "+this.push18);
		write.println("push19: "+this.push19);
		write.println("push20: "+this.push20);
		write.println("push21: "+this.push21);
		write.println("push22: "+this.push22);
		write.println("push23: "+this.push23);
		write.println("push24: "+this.push24);
		write.println("push25: "+this.push25);
		write.println("push26: "+this.push26);
		write.println("push27: "+this.push27);
		write.println("push28: "+this.push28);
		write.println("push29: "+this.push29);
		write.println("push30: "+this.push30);
		write.println("push31: "+this.push31);
		write.println("push32: "+this.push32);
		write.println("dup1: "+this.dup1);
		write.println("dup2: "+this.dup2);
		write.println("dup3: "+this.dup3);
		write.println("dup4: "+this.dup4);
		write.println("dup5: "+this.dup5);
		write.println("dup6: "+this.dup6);
		write.println("dup7: "+this.dup7);
		write.println("dup8: "+this.dup8);
		write.println("dup9: "+this.dup9);
		write.println("dup10: "+this.dup10);
		write.println("dup11: "+this.dup11);
		write.println("dup12: "+this.dup12);
		write.println("dup13: "+this.dup13);
		write.println("dup14: "+this.dup14);
		write.println("dup15: "+this.dup15);
		write.println("dup16: "+this.dup16);
		write.println("swap1: "+this.swap1);
		write.println("swap2: "+this.swap2);
		write.println("swap3: "+this.swap3);
		write.println("swap4: "+this.swap4);
		write.println("swap5: "+this.swap5);
		write.println("swap6: "+this.swap6);
		write.println("swap7: "+this.swap7);
		write.println("swap8: "+this.swap8);
		write.println("swap9: "+this.swap9);
		write.println("swap10: "+this.swap10);
		write.println("swap11: "+this.swap11);
		write.println("swap12: "+this.swap12);
		write.println("swap13: "+this.swap13);
		write.println("swap14: "+this.swap14);
		write.println("swap15: "+this.swap15);
		write.println("swap16: "+this.swap16);
		write.close();
    }
    
    public static void main(String[] args) throws Exception {
    	BlocksScanner ss = new BlocksScanner();
    	// Ottiene il numero corrente di blocchi
    	int index = Integer.parseInt(getBlocksNumber());
    	System.out.println("Blocco corrente: "+index);
    	System.out.println("Inizio scansione indirizzi contratti");
    	for(int i = index; i >= 0; i--) {    		
    		ss.getAddresses("https://etherscan.io/txs?block="+i);
    	}
    	// Ottiene gli opcode e la versione del compilatore
    	System.out.println("Inizio scansione Opcode & versione compilatore Solidity");
    	File file = new File("AddressesBlockchain.txt");
    	BufferedReader br = new BufferedReader(new FileReader(file));
    	    String line;
    	    while ((line = br.readLine()) != null) {    	    	
    	    	ss.getOpcode("https://etherscan.io/address/"+line+"#code");
    	    	ss.getCompilerVersion("https://etherscan.io/address/"+line+"#code");
    	    }
    	// Analizza gli opcode
    	System.out.println("Inizio statistiche");
    	ss.statistics();
    	// Memorizza i risultati 
    	System.out.println("Scrittura risultati in corso");
    	ss.writeResults();
    	br.close();
    }
}

