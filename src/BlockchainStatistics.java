import java.net.*;
import java.io.*;

public class BlockchainStatistics {

	// Lista di OpCode : http://solidity.readthedocs.io/en/develop/assembly.html
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
	private int number;;
	private int difficulty;
	private int gaslimit;
	
	public static String getPages() throws Exception {
		URL website = new URL("https://etherscan.io/txs");
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
            	if (part.contains("href=\"txs?p=") == true) {
            		int index = part.lastIndexOf("\"");
            		line = part.substring(12, index);
            		
            	}
            }	
        }
        return line;
	}
	
	/**
	 * Ottiene gli indirizzi dei contratti e li memorizza su file
	 * @param url			url pagine contenenti i contratti
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

        FileOutputStream output = new FileOutputStream("Addresses.txt", true);
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
     * Ottiene gli Opcode tramite il codice sorgente di ogni pagina dei contratti
     * e li memorizza su file
     * @param url		url del contratto
     * @throws Exception
     */
    public void getOpcode(String url) throws Exception {
    	 URL website = new URL(url);
         URLConnection connection = website.openConnection();
         connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
         connection.connect();
         BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        
        FileOutputStream output = new FileOutputStream("Opcode.txt", true);
		PrintStream write = new PrintStream(output);
	
        String inputLine;
        boolean opcode = false;
        while ((inputLine = in.readLine()) != null) {
            	if (inputLine.contains("assembly {") == true) {
            		opcode=true;
            	}
            	if(inputLine.contains("}") == true) {
            		opcode = false;
            	}
            	if(opcode == true) {
            		if(inputLine.contains("//") == false && !inputLine.isEmpty()) write.println(inputLine);
            	}
        }  
        in.close();
        write.close();
    }

    /**
     * Effetua la statistica degli opcode dei contratti
     * @throws Exception
     */
    public void statistics() throws Exception {
    	File file = new File("OpCode.txt");
    	BufferedReader br = new BufferedReader(new FileReader(file));
    	String line;
    	while ((line = br.readLine()) != null) {
    		if (line.contains("add") == true) {
				this.add++;
    		}	
    		if (line.contains("mul") == true) {
				this.mul++;
    		}	
    		if (line.contains("sub") == true) {
				this.sub++;
    		}	
    		if (line.contains("div") == true) {
				this.div++;
    		}	
    		if (line.contains("sdiv") == true) {
				this.sdiv++;
    		}	
    		if (line.contains("mod") == true) {
				this.mod++;
    		}	
    		if (line.contains("smod") == true) {
				this.smod++;
    		}	
    		if (line.contains("exp") == true) {
				this.exp++;
    		}
    		if (line.contains("not") == true) {
				this.not++;
    		}	
    		if (line.contains("if") == true) {
				this.if1++;
    		}	
    		if (line.contains("lt") == true) {
				this.lt++;
    		}	
    		if (line.contains("gt") == true) {
				this.gt++;
    		}
    		if (line.contains("slt") == true) {
				this.slt++;
    		}
    		if (line.contains("sgt") == true) {
				this.sgt++;
    		}	
    		if (line.contains("eq") == true) {
				this.eq++;
    		}	
    		if (line.contains("iszero") == true) {
				this.iszero++;
    		}	
    		if (line.contains("and") == true) {
				this.and++;
    		}
    		if (line.contains("or") == true) {
				this.or++;
    		}
    		if (line.contains("xor") == true) {
				this.xor++;
    		}
    		if (line.contains("byte") == true) {
				this.byte1++;
    		}	
    		if (line.contains("addmod") == true) {
				this.addmod++;
    		}	
    		if (line.contains("mulmod") == true) {
				this.mulmod++;
    		}	
    		if (line.contains("signextend") == true) {
				this.signextend++;
    		}
    		if (line.contains("keccak256") == true) {
				this.keccak256++;
    		}	
    		if (line.contains("sha3") == true) {
				this.sha3++;
    		}	
    		if (line.contains("jump") == true) {
				this.jump++;
    		}	
    		if (line.contains("jumpi") == true) {
				this.jumpi++;
    		}
    		if (line.contains("pop") == true) {
				this.pop++;
    		}
    		if (line.contains("mload") == true) {
				this.mload++;
    		}	
    		if (line.contains("sha3") == true) {
				this.sha3++;
    		}	
    		if (line.contains("mstore") == true) {
				this.mstore++;
    		}	
    		if (line.contains("mstore8") == true) {
				this.mstore8++;
    		}
    		if (line.contains("sload") == true) {
				this.sload++;
    		}
    		if (line.contains("sstore") == true) {
				this.sstore++;
    		}
    		if (line.contains("msize") == true) {
				this.msize++;
    		}	
    		if (line.contains("gas") == true) {
				this.gas++;
    		}	
    		if (line.contains("address") == true) {
				this.address++;
    		}	
    		if (line.contains("balance") == true) {
				this.balance++;
    		}
    		if (line.contains("caller") == true) {
				this.caller++;
    		}
    		if (line.contains("callvalue") == true) {
				this.callvalue++;
    		}
    		if (line.contains("calldataload") == true) {
				this.calldataload++;
    		}	
    		if (line.contains("calldatasize") == true) {
				this.calldatasize++;
    		}	
    		if (line.contains("calldatacopy") == true) {
				this.calldatacopy++;
    		}	
    		if (line.contains("codesize") == true) {
				this.codesize++;
    		}
    		if (line.contains("codecopy") == true) {
				this.codecopy++;
    		}
    		if (line.contains("extcodesize") == true) {
				this.extcodesize++;
    		}
    		if (line.contains("extcodecopy") == true) {
				this.extcodecopy++;
    		}
    		if (line.contains("returndatasize") == true) {
				this.returndatasize++;
    		}	
    		if (line.contains("returndatacopy") == true) {
				this.returndatacopy++;
    		}	
    		if (line.contains("create") == true) {
				this.create++;
    		}	
    		if (line.contains("create2") == true) {
				this.create2++;
    		}
    		if (line.contains("call") == true) {
				this.call++;
    		}
    		if (line.contains("callcode") == true) {
				this.callcode++;
    		}
    		if (line.contains("delegatecall") == true) {
				this.delegatecall++;
    		}
    		if (line.contains("staticcall") == true) {
				this.staticcall++;
    		}
    		if (line.contains("return") == true) {
				this.return1++;
    		}	
    		if (line.contains("revert") == true) {
				this.revert++;
    		}	
    		if (line.contains("selfdestruct") == true) {
				this.selfdestruct++;
    		}	
    		if (line.contains("invalid") == true) {
				this.invalid++;
    		}
    		if (line.contains("log0") == true) {
				this.log0++;
    		}	
    		if (line.contains("log1") == true) {
				this.log1++;
    		}
    		if (line.contains("log2") == true) {
				this.log2++;
    		}
    		if (line.contains("log3") == true) {
				this.log3++;
    		}
    		if (line.contains("log4") == true) {
				this.log4++;
    		}
    		if (line.contains("origin") == true) {
				this.origin++;
    		}
    		if (line.contains("gasprice") == true) {
				this.gasprice++;
    		}	
    		if (line.contains("blockhash") == true) {
				this.blockhash++;
    		}	
    		if (line.contains("coinbase") == true) {
				this.coinbase++;
    		}	
    		if (line.contains("timestamp") == true) {
				this.timestamp++;
    		}
    		if (line.contains("number") == true) {
				this.number++;
    		}	
    		if (line.contains("difficulty") == true) {
				this.difficulty++;
    		}	
    		if (line.contains("gaslimit") == true) {
				this.gaslimit++;
    		}
    	}
    	br.close();
    }
    
    /**
     * Memorizza i risultati della statistica su file
     * @throws Exception
     */
    public void writeResults() throws Exception {
        FileOutputStream output = new FileOutputStream("Results.txt", true);
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
		write.close();
    }
    
    public static void main(String[] args) throws Exception {
    	BlockchainStatistics ss = new BlockchainStatistics();
    	// Crea la lista degli indirizzi
    	int index = Integer.parseInt(getPages());
    	System.out.println(index);
    	  for(int i=1; i<=index; i++) {
    		if(i==1) {
    			ss.getAddresses("https://etherscan.io/txs");
    		}
    		else {
    			ss.getAddresses("https://etherscan.io/txs?p="+i);
    		}
    	}
    	// Ottiene gli opcode 
    	File file = new File("Addresses.txt");
    	BufferedReader br = new BufferedReader(new FileReader(file));
    	    String line;
    	    while ((line = br.readLine()) != null) {
    	    	ss.getOpcode("https://etherscan.io/address/"+line+"#code");
    	    }
    	// Analizza gli opcode
    	ss.statistics();
    	// Memorizza i risultati    	
    	ss.writeResults();
    	br.close();
    }
}
