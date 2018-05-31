import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

public class ScannerSolidityCompiler {
		
		/*
		 * Lista di file contenuti in solidity-develop
		 */
		private ArrayList<Path> files = new ArrayList<>();
		
		/*
		 * Paths dei file contenenti la lista degli opcode utilizzati
		 * dal compilatore Solidity
		 */
		private ArrayList<String> paths = new ArrayList<String>();
		
		/*
		 * Directory delle versioni del compilatore solidity da analizzare
		 */
		private static ArrayList<Path> directory = new ArrayList<>();
		
		/*
		 * Instruction Set presente nello Yellow Paper
		 */	
		
		// Stop and Arithmetic Operations
		private int STOP;
		private int ADD;
		private int MUL;
		private int SUB;
		private int DIV;
		private int SDIV;
		private int MOD;
		private int SMOD;
		private int ADDMOD;
		private int MULMOD;
		private int EXP;
		private int SIGNEXTEND;	
		// Comparison & Bitwise Logic Operations
		private int LT;
		private int GT;
		private int SLT;
		private int SGT;
		private int EQ;		
		private int ISZERO;
		private int AND;
		private int OR;
		private int XOR;		
		private int NOT;
		private int BYTE;
		// SHA3
		private int SHA3;
		// Environmental Information
		private int ADDRESS;
		private int BALANCE;
		private int ORIGIN;
		private int CALLER;
		private int CALLVALUE;
		private int CALLDATALOAD;		
		private int CALLDATASIZE;
		private int CALLDATACOPY;
		private int CODESIZE;
		private int CODECOPY;
		private int GASPRICE;		
		private int EXTCODESIZE;
		private int EXTCODECOPY;
		private int RETURNDATASIZE;
		private int RETURNDATACOPY;
		// Block Information
		private int BLOCKHASH;
		private int COINBASE;
		private int TIMESTAMP;
		private int NUMBER;
		private int DIFFICULTY;
		private int GASLIMIT;
		// Stack, Memory, Storage and Flow Operations
		private int POP;
		private int MLOAD;
		private int MSTORE;
		private int MSTORE8;
		private int SLOAD;
		private int SSTORE;
		private int JUMP;
		private int JUMPI;
		private int PC;		
		private int MSIZE;
		private int GAS;
		private int JUMPDEST;
		// Push Operations
		private int PUSH1;
		private int PUSH2;
		private int PUSH3;
		private int PUSH4;
		private int PUSH5;
		private int PUSH6;
		private int PUSH7;
		private int PUSH8;
		private int PUSH9;
		private int PUSH10;		
		private int PUSH11;
		private int PUSH12;
		private int PUSH13;
		private int PUSH14;
		private int PUSH15;		
		private int PUSH16;
		private int PUSH17;
		private int PUSH18;
		private int PUSH19;		
		private int PUSH20;
		private int PUSH21;
		private int PUSH22;
		private int PUSH23;				
		private int PUSH24;
		private int PUSH25;
		private int PUSH26;
		private int PUSH27;
		private int PUSH28;
		private int PUSH29;
		private int PUSH30;
		private int PUSH31;		
		private int PUSH32;		
		// Duplication Operations
		private int DUP1;
		private int DUP2;
		private int DUP3;
		private int DUP4;
		private int DUP5;
		private int DUP6;		
		private int DUP7;
		private int DUP8;
		private int DUP9;
		private int DUP10;		
		private int DUP11;
		private int DUP12;
		private int DUP13;
		private int DUP14;
		private int DUP15;
		private int DUP16;
		// Exchange Operations
		private int SWAP1;
		private int SWAP2;
		private int SWAP3;
		private int SWAP4;
		private int SWAP5;
		private int SWAP6;		
		private int SWAP7;
		private int SWAP8;
		private int SWAP9;
		private int SWAP10;		
		private int SWAP11;
		private int SWAP12;
		private int SWAP13;
		private int SWAP14;
		private int SWAP15;
		private int SWAP16;
		// Logging Operations
		private int LOG0;
		private int LOG1;
		private int LOG2;
		private int LOG3;
		private int LOG4;	
		// System operations
		private int CREATE;
		private int CALL;
		private int CALLCODE;
		private int RETURN;		
		private int DELEGATECALL;
		private int STATICCALL;
		private int REVERT;
		private int INVALID;		
		private int SELFDESTRUCT;

		
		/**
		 * Ottiene i paths delle directory dei vari compilatori di Solidity da analizzare
		 * @param path - directory contenente le directory dei compilatori
		 * @throws IOException
		 */
		public void listSolidityCompilerVersion(Path path) throws IOException {
		    try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
		        for (Path entry : stream) {
		            if (Files.isDirectory(entry)) {
		                directory.add(entry);
		            }
		        }
		    }			
		}
		
		/**
		 * Ottiene la lista dei file presenti nella directory del compilatore Solidity
		 * @param path - percorso alla directory del compilatore
		 * @throws IOException
		 */
		public void listFiles(Path path) throws IOException {
		    try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
		        for (Path entry : stream) {
		            if (Files.isDirectory(entry)) {
		                listFiles(entry);
		            }
		            files.add(entry);
		        }
		    }
		}
		
		
		/**
		 * Verifica in quali file è presente la dichiarazione degli opcode
		 * @throws Exception
		 */
	    public void opcodeDeclaration() throws Exception {
	    	paths.clear();
	    	Iterator<Path> i = files.iterator();
		    while(i.hasNext()) {
			            	String app = i.next().toString();
			            	if(app.contains("Instruction")) {
			            		paths.add(app);			            	
			                }	
		    } 
	    }
	    
	    /**
	     * Verifica quali opcode sono utilizzati dal compilatore
	     * @throws Exception
	     */
	    public void statistics() throws Exception {
	    	Iterator<String> i = paths.iterator();
	    	while(i.hasNext()) {
		    	File file = new File(i.next());
		    	BufferedReader br = new BufferedReader(new FileReader(file));
		    	String line;
		    	while ((line = br.readLine()) != null) {
		    		if(line.contains("STOP")) this.STOP++;
		    		if(line.contains("ADD")) this.ADD++;
		    		if(line.contains("MUL")) this.MUL++;
		    	    if(line.contains("SUB")) this.SUB++;
		    	    if(line.contains("DIV")) this.DIV++;
		    	    if(line.contains("SDIV")) this.SDIV++;
		    	    if(line.contains("MOD")) this.MOD++;
		    	    if(line.contains("SMOD")) this.SMOD++;
		    	    if(line.contains("ADDMOD")) this.ADDMOD++;
		    	    if(line.contains("MULMOD")) this.MULMOD++;
		    	    if(line.contains("EXP")) this.EXP++;
		    	    if(line.contains("SIGNEXTEND")) this.SIGNEXTEND++;	
		    	    if(line.contains("LT")) this.LT++;
		    	    if(line.contains("GT")) this.GT++; 
		    	    if(line.contains("SLT")) this.SLT++;
		    	    if(line.contains("SGT")) this.SGT++;
				    if(line.contains("EQ")) this.EQ++;		
					if(line.contains("ISZERO")) this.ISZERO++;
					if(line.contains("AND")) this.AND++;
					if(line.contains("OR")) this.OR++;
					if(line.contains("XOR")) this.XOR++;		
					if(line.contains("NOT")) this.NOT++;
					if(line.contains("BYTE")) this.BYTE++;
					if(line.contains("SHA3")) this.SHA3++;
					if(line.contains("ADDRESS")) this.ADDRESS++;
					if(line.contains("BALANCE")) this.BALANCE++;
					if(line.contains("ORIGIN")) this.ORIGIN++;
					if(line.contains("CALLER")) this.CALLER++;
					if(line.contains("CALLVALUE")) this.CALLVALUE++;
					if(line.contains("CALLDATALOAD")) this.CALLDATALOAD++;		
					if(line.contains("CALLDATASIZE")) this.CALLDATASIZE++;
					if(line.contains("CALLDATACOPY")) this.CALLDATACOPY++;
					if(line.contains("CODESIZE")) this.CODESIZE++;
					if(line.contains("CODECOPY")) this.CODECOPY++;
					if(line.contains("GASPRICE")) this.GASPRICE++;		
					if(line.contains("EXTCODESIZE")) this.EXTCODESIZE++;
					if(line.contains("EXTCODECOPY")) this.EXTCODECOPY++;
					if(line.contains("RETURNDATASIZE")) this.RETURNDATASIZE++;
					if(line.contains("RETURNDATACOPY")) this.RETURNDATACOPY++;
					if(line.contains("BLOCKHASH")) this.BLOCKHASH++;
					if(line.contains("COINBASE")) this.COINBASE++;
					if(line.contains("TIMESTAMP")) this.TIMESTAMP++;
					if(line.contains("NUMBER")) this.NUMBER++;
					if(line.contains("DIFFICULTY")) this.DIFFICULTY++;
					if(line.contains("GASLIMIT")) this.GASLIMIT++;
					if(line.contains("POP")) this.POP++;
					if(line.contains("MLOAD")) this.MLOAD++;
					if(line.contains("MSTORE")) this.MSTORE++;
					if(line.contains("MSTORE8")) this.MSTORE8++;
					if(line.contains("SLOAD")) this.SLOAD++;
					if(line.contains("SSTORE")) this.SSTORE++;
					if(line.contains("JUMP")) this.JUMP++;
					if(line.contains("JUMPI")) this.JUMPI++;
					if(line.contains("PC")) this.PC++;		
					if(line.contains("MSIZE")) this.MSIZE++;
					if(line.contains("GAS")) this.GAS++;
					if(line.contains("JUMPDEST")) this.JUMPDEST++;
					if(line.contains("PUSH1")) this.PUSH1++;
					if(line.contains("PUSH2")) this.PUSH2++;
					if(line.contains("PUSH3")) this.PUSH3++;
					if(line.contains("PUSH4")) this.PUSH4++;
					if(line.contains("PUSH5")) this.PUSH5++;
					if(line.contains("PUSH6")) this.PUSH6++;
					if(line.contains("PUSH7")) this.PUSH7++;
					if(line.contains("PUSH8")) this.PUSH8++;
					if(line.contains("PUSH9")) this.PUSH9++;
					if(line.contains("PUSH10")) this.PUSH10++;		
					if(line.contains("PUSH11")) this.PUSH11++;
					if(line.contains("PUSH12")) this.PUSH12++;
					if(line.contains("PUSH13")) this.PUSH13++;
					if(line.contains("PUSH14")) this.PUSH14++;
					if(line.contains("PUSH15")) this.PUSH15++;		
					if(line.contains("PUSH16")) this.PUSH16++;
					if(line.contains("PUSH17")) this.PUSH17++;
					if(line.contains("PUSH18")) this.PUSH18++;
					if(line.contains("PUSH19")) this.PUSH19++;		
					if(line.contains("PUSH20")) this.PUSH20++;
					if(line.contains("PUSH21")) this.PUSH21++;
					if(line.contains("PUSH22")) this.PUSH22++;
					if(line.contains("PUSH23")) this.PUSH23++;				
					if(line.contains("PUSH24")) this.PUSH24++;
					if(line.contains("PUSH25")) this.PUSH25++;
					if(line.contains("PUSH26")) this.PUSH26++;
					if(line.contains("PUSH27")) this.PUSH27++;
					if(line.contains("PUSH28")) this.PUSH28++;
					if(line.contains("PUSH29")) this.PUSH29++;
					if(line.contains("PUSH30")) this.PUSH30++;
					if(line.contains("PUSH31")) this.PUSH31++;		
					if(line.contains("PUSH32")) this.PUSH32++;		
					if(line.contains("DUP1")) this.DUP1++;
					if(line.contains("DUP2")) this.DUP2++;
					if(line.contains("DUP3")) this.DUP3++;
		    		if(line.contains("DUP4")) this.DUP4++;
		    		if(line.contains("DUP5")) this.DUP5++;
		    		if(line.contains("DUP6")) this.DUP6++;		
		    		if(line.contains("DUP7")) this.DUP7++;
		    		if(line.contains("DUP8")) this.DUP8++;
		    		if(line.contains("DUP9")) this.DUP9++;
		    		if(line.contains("DUP10")) this.DUP10++;		
		    		if(line.contains("DUP11")) this.DUP11++;
		    		if(line.contains("DUP12")) this.DUP12++;
		    		if(line.contains("DUP13")) this.DUP13++;
		    		if(line.contains("DUP14")) this.DUP14++;
		    		if(line.contains("DUP15")) this.DUP15++;
		    		if(line.contains("DUP16")) this.DUP16++;
		    		if(line.contains("SWAP1")) this.SWAP1++;
		    		if(line.contains("SWAP2")) this.SWAP2++;
		    		if(line.contains("SWAP3")) this.SWAP3++;
		    		if(line.contains("SWAP4")) this.SWAP4++;
		    		if(line.contains("SWAP5")) this.SWAP5++;
		    		if(line.contains("SWAP6")) this.SWAP6++;		
		    		if(line.contains("SWAP7")) this.SWAP7++;
		    		if(line.contains("SWAP8")) this.SWAP8++;
		    		if(line.contains("SWAP9")) this.SWAP9++;
		    		if(line.contains("SWAP10")) this.SWAP10++;		
		    		if(line.contains("SWAP11")) this.SWAP11++;
		    		if(line.contains("SWAP12")) this.SWAP12++;
		    		if(line.contains("SWAP13")) this.SWAP13++;
		    		if(line.contains("SWAP14")) this.SWAP14++;
		    		if(line.contains("SWAP15")) this.SWAP15++;
		    		if(line.contains("SWAP16")) this.SWAP16++;
		    		if(line.contains("LOG0")) this.LOG0++;
		    		if(line.contains("LOG1")) this.LOG1++;
		    		if(line.contains("LOG2")) this.LOG2++;
		    		if(line.contains("LOG3")) this.LOG3++;
		    		if(line.contains("LOG4")) this.LOG4++;	
		    		if(line.contains("CREATE")) this.CREATE++;
		    		if(line.contains("CALL")) this.CALL++;
		    		if(line.contains("CALLCODE")) this.CALLCODE++;
		    		if(line.contains("RETURN")) this.RETURN++;		
		    		if(line.contains("DELEGATECALL")) this.DELEGATECALL++;
		    		if(line.contains("STATICCALL")) this.STATICCALL++;
		    		if(line.contains("REVERT")) this.REVERT++;
		    		if(line.contains("INVALID")) this.INVALID++;		
		    		if(line.contains("SELFDESTRUCT")) this.SELFDESTRUCT++;		    		
		    	}
		    	br.close();
	    	}
	    }
	    
	    /**
	     * Riporta i risultati su file
	     * @param path - percorso della directory
	     * @throws Exception
	     */
	    public void writeResults(String path) throws Exception {
	        FileOutputStream output = new FileOutputStream("Results-Solidity-develop"+path.substring(55, path.length())+".txt", true);
			PrintStream write = new PrintStream(output);
			write.println("Path file contenenti la dichiarazione degli OpCode: ");
			Iterator<String> i = paths.iterator();
			while(i.hasNext()) {
				write.println(i.next());
			}
			write.println();
			write.println("Lista degli OpCode utilizzati:");
			if(this.STOP > 0 ) write.println("STOP");
			else write.println("STOP non utilizzato");
			if(this.ADD > 0 ) write.println("ADD");
			else write.println("ADD non utilizzato");
			if(this.MUL > 0 ) write.println("MUL");
			else write.println("MUL non utilizzato");
			if(this.SUB > 0 ) write.println("SUB");
			else write.println("SUB non utilizzato");
			if(this.DIV > 0 ) write.println("DIV");
			else write.println("DIV non utilizzato");
			if(this.SDIV > 0 ) write.println("SDIV");
			else write.println("SDIV non utilizzato");
			if(this.MOD > 0 ) write.println("MOD");
			else write.println("MOD non utilizzato");
			if(this.SMOD > 0 ) write.println("SMOD");
			else write.println("SMOD non utilizzato");
			if(this.ADDMOD > 0 ) write.println("ADDMOD");
			else write.println("ADDMOD non utilizzato");			
			if(this.MULMOD > 0 ) write.println("MULMOD");
			else write.println("MULMOD non utilizzato");
			if(this.EXP > 0 ) write.println("EXP");
			else write.println("EXP non utilizzato");
			if(this.SIGNEXTEND > 0 ) write.println("SIGNEXTEND");
			else write.println("SIGNEXTEND non utilizzato");
			if(this.LT > 0 ) write.println("LT");
			else write.println("LT non utilizzato");
			if(this.GT > 0 ) write.println("GT");
			else write.println("GT non utilizzato");
			if(this.SLT > 0 ) write.println("SLT");
			else write.println("SLT non utilizzato");
			if(this.SGT > 0 ) write.println("SGT");
			else write.println("SGT non utilizzato");
			if(this.EQ > 0 ) write.println("EQ");
			else write.println("EQ non utilizzato");			
			if(this.ISZERO > 0 ) write.println("ISZERO");
			else write.println("ISZERO non utilizzato");
			if(this.AND > 0 ) write.println("AND");
			else write.println("AND non utilizzato");
			if(this.OR > 0 ) write.println("OR");
			else write.println("OR non utilizzato");
			if(this.XOR > 0 ) write.println("XOR");
			else write.println("XOR non utilizzato");
			if(this.NOT > 0 ) write.println("NOT");
			else write.println("NOT non utilizzato");			
			if(this.BYTE > 0 ) write.println("BYTE");
			else write.println("BYTE non utilizzato");
			if(this.SHA3 > 0 ) write.println("SHA3");
			else write.println("SHA3 non utilizzato");
			if(this.ADDRESS > 0 ) write.println("ADDRESS");
			else write.println("ADDRESS non utilizzato");
			if(this.BALANCE > 0 ) write.println("BALANCE");
			else write.println("BALANCE non utilizzato");
			if(this.ORIGIN > 0 ) write.println("ORIGIN");
			else write.println("ORIGIN non utilizzato");			
			if(this.CALLER > 0 ) write.println("CALLER");
			else write.println("CALLER non utilizzato");
			if(this.CALLVALUE > 0 ) write.println("CALLVALUE");
			else write.println("CALLVALUE non utilizzato");
			if(this.CALLDATALOAD > 0 ) write.println("CALLDATALOAD");
			else write.println("CALLDATALOAD non utilizzato");
			if(this.CALLDATASIZE > 0 ) write.println("CALLDATASIZE");
			else write.println("CALLDATASIZE non utilizzato");
			if(this.CALLDATACOPY > 0 ) write.println("CALLDATACOPY");
			else write.println("CALLDATACOPY non utilizzato");			
			if(this.CODESIZE > 0 ) write.println("CODESIZE");
			else write.println("CODESIZE non utilizzato");
			if(this.CODECOPY > 0 ) write.println("CODECOPY");
			else write.println("CODECOPY non utilizzato");
			if(this.GASPRICE > 0 ) write.println("GASPRICE");
			else write.println("GASPRICE non utilizzato");
			if(this.EXTCODESIZE > 0 ) write.println("EXTCODESIZE");
			else write.println("EXTCODESIZE non utilizzato");			
			if(this.EXTCODECOPY > 0 ) write.println("EXTCODECOPY");
			else write.println("EXTCODECOPY non utilizzato");
			if(this.RETURNDATASIZE > 0 ) write.println("RETURNDATASIZE");
			else write.println("RETURNDATASIZE non utilizzato");
			if(this.RETURNDATACOPY > 0 ) write.println("RETURNDATACOPY");
			else write.println("RETURNDATACOPY non utilizzato");
			if(this.BLOCKHASH > 0 ) write.println("BLOCKHASH");
			else write.println("BLOCKHASH non utilizzato");
			if(this.COINBASE > 0 ) write.println("COINBASE");
			else write.println("COINBASE non utilizzato");			
			if(this.TIMESTAMP > 0 ) write.println("TIMESTAMP");
			else write.println("TIMESTAMP non utilizzato");
			if(this.NUMBER > 0 ) write.println("NUMBER");
			else write.println("NUMBER non utilizzato");
			if(this.DIFFICULTY > 0 ) write.println("DIFFICULTY");
			else write.println("DIFFICULTY non utilizzato");
			if(this.GASLIMIT > 0 ) write.println("GASLIMIT");
			else write.println("GASLIMIT non utilizzato");
			if(this.POP > 0 ) write.println("POP");
			else write.println("POP non utilizzato");
			if(this.MLOAD > 0 ) write.println("MLOAD");
			else write.println("MLOAD non utilizzato");
			if(this.MSTORE > 0 ) write.println("MSTORE");
			else write.println("MSTORE non utilizzato");
			if(this.MSTORE8 > 0 ) write.println("MSTORE8");
			else write.println("MSTORE8 non utilizzato");			
			if(this.SLOAD > 0 ) write.println("SLOAD");
			else write.println("SLOAD non utilizzato");
			if(this.SSTORE > 0 ) write.println("SSTORE");
			else write.println("SSTORE non utilizzato");
			if(this.JUMP > 0 ) write.println("JUMP");
			else write.println("JUMP non utilizzato");
			if(this.JUMPI > 0 ) write.println("JUMPI");
			else write.println("JUMPI non utilizzato");
			if(this.PC > 0 ) write.println("PC");
			else write.println("PC non utilizzato");			
			if(this.MSIZE > 0 ) write.println("MSIZE");
			else write.println("MSIZE non utilizzato");
			if(this.GAS > 0 ) write.println("GAS");
			else write.println("GAS non utilizzato");
			if(this.JUMPDEST > 0 ) write.println("JUMPDEST");
			else write.println("JUMPDEST non utilizzato");
			if(this.PUSH1 > 0 ) write.println("PUSH1");
			else write.println("PUSH1 non utilizzato");
			if(this.PUSH2 > 0 ) write.println("PUSH2");
			else write.println("PUSH2 non utilizzato");			
			if(this.PUSH3 > 0 ) write.println("PUSH3");
			else write.println("PUSH3 non utilizzato");
			if(this.PUSH4 > 0 ) write.println("PUSH4");
			else write.println("PUSH4 non utilizzato");
			if(this.PUSH5 > 0 ) write.println("PUSH5");
			else write.println("PUSH5 non utilizzato");
			if(this.PUSH6 > 0 ) write.println("PUSH6");
			else write.println("PUSH6 non utilizzato");			
			if(this.PUSH7 > 0 ) write.println("PUSH7");
			else write.println("PUSH7 non utilizzato");
			if(this.PUSH8 > 0 ) write.println("PUSH8");
			else write.println("PUSH8 non utilizzato");
			if(this.PUSH9 > 0 ) write.println("PUSH9");
			else write.println("PUSH9 non utilizzato");
			if(this.PUSH10 > 0 ) write.println("PUSH10");
			else write.println("PUSH10 non utilizzato");
			if(this.PUSH11 > 0 ) write.println("PUSH11");
			else write.println("PUSH11 non utilizzato");
			if(this.PUSH12 > 0 ) write.println("PUSH12");
			else write.println("PUSH12 non utilizzato");			
			if(this.PUSH13 > 0 ) write.println("PUSH13");
			else write.println("PUSH13 non utilizzato");
			if(this.PUSH14 > 0 ) write.println("PUSH14");
			else write.println("PUSH14 non utilizzato");
			if(this.PUSH15 > 0 ) write.println("PUSH15");
			else write.println("PUSH15 non utilizzato");
			if(this.PUSH16 > 0 ) write.println("PUSH16");
			else write.println("PUSH16 non utilizzato");
			if(this.PUSH17 > 0 ) write.println("PUSH17");
			else write.println("PUSH17 non utilizzato");			
			if(this.PUSH18 > 0 ) write.println("PUSH18");
			else write.println("PUSH18 non utilizzato");
			if(this.PUSH19 > 0 ) write.println("PUSH19");
			else write.println("PUSH19 non utilizzato");
			if(this.PUSH20 > 0 ) write.println("PUSH20");
			else write.println("PUSH20 non utilizzato");
			if(this.PUSH21 > 0 ) write.println("PUSH21");
			else write.println("PUSH21 non utilizzato");		
			if(this.PUSH22 > 0 ) write.println("PUSH22");
			else write.println("PUSH22 non utilizzato");
			if(this.PUSH23 > 0 ) write.println("PUSH23");
			else write.println("PUSH23 non utilizzato");
			if(this.PUSH24 > 0 ) write.println("PUSH24");
			else write.println("PUSH24 non utilizzato");
			if(this.PUSH25 > 0 ) write.println("PUSH25");
			else write.println("PUSH25 non utilizzato");
			if(this.PUSH26 > 0 ) write.println("PUSH26");
			else write.println("PUSH26 non utilizzato");			
			if(this.PUSH27 > 0 ) write.println("PUSH27");
			else write.println("PUSH27 non utilizzato");
			if(this.PUSH28 > 0 ) write.println("PUSH28");
			else write.println("PUSH28 non utilizzato");
			if(this.PUSH29 > 0 ) write.println("PUSH29");
			else write.println("PUSH29 non utilizzato");
			if(this.PUSH30 > 0 ) write.println("PUSH30");
			else write.println("PUSH30 non utilizzato");			
			if(this.PUSH31 > 0 ) write.println("PUSH31");
			else write.println("PUSH31 non utilizzato");
			if(this.PUSH32 > 0 ) write.println("PUSH32");
			else write.println("PUSH32 non utilizzato");
			if(this.DUP1 > 0 ) write.println("DUP1");
			else write.println("DUP1 non utilizzato");			
			if(this.DUP2 > 0 ) write.println("DUP2");
			else write.println("DUP2 non utilizzato");
			if(this.DUP3 > 0 ) write.println("DUP3");
			else write.println("DUP3 non utilizzato");
			if(this.DUP4 > 0 ) write.println("DUP4");
			else write.println("DUP4 non utilizzato");
			if(this.DUP5 > 0 ) write.println("DUP5");
			else write.println("DUP5 non utilizzato");
			if(this.DUP6 > 0 ) write.println("DUP6");
			else write.println("DUP6 non utilizzato");
			if(this.DUP7 > 0 ) write.println("DUP7");
			else write.println("DUP7 non utilizzato");
			if(this.DUP8 > 0 ) write.println("DUP8");
			else write.println("DUP8 non utilizzato");		
			if(this.DUP9 > 0 ) write.println("DUP9");
			else write.println("DUP9 non utilizzato");
			if(this.DUP10 > 0 ) write.println("DUP10");
			else write.println("DUP10 non utilizzato");
			if(this.DUP11 > 0 ) write.println("DUP11");
			else write.println("DUP11 non utilizzato");
			if(this.DUP12 > 0 ) write.println("DUP12");
			else write.println("DUP12 non utilizzato");		
			if(this.DUP13 > 0 ) write.println("DUP13");
			else write.println("DUP13 non utilizzato");
			if(this.DUP14 > 0 ) write.println("DUP14");
			else write.println("DUP14 non utilizzato");
			if(this.DUP15 > 0 ) write.println("DUP15");
			else write.println("DUP15 non utilizzato");
			if(this.DUP16 > 0 ) write.println("DUP16");
			else write.println("DUP16 non utilizzato");
			if(this.SWAP1 > 0 ) write.println("SWAP1");
			else write.println("SWAP1 non utilizzato");
			if(this.SWAP2 > 0 ) write.println("SWAP2");
			else write.println("SWAP2 non utilizzato");
			if(this.SWAP3 > 0 ) write.println("SWAP3");
			else write.println("SWAP3 non utilizzato");			
			if(this.SWAP4 > 0 ) write.println("SWAP4");
			else write.println("SWAP4 non utilizzato");
			if(this.SWAP5 > 0 ) write.println("SWAP5");
			else write.println("SWAP5 non utilizzato");
			if(this.SWAP6 > 0 ) write.println("SWAP6");
			else write.println("SWAP6 non utilizzato");
			if(this.SWAP7 > 0 ) write.println("SWAP7");
			else write.println("SWAP7 non utilizzato");			
			if(this.SWAP8 > 0 ) write.println("SWAP8");
			else write.println("SWAP8 non utilizzato");
			if(this.SWAP9 > 0 ) write.println("SWAP9");
			else write.println("SWAP9 non utilizzato");
			if(this.SWAP10 > 0 ) write.println("SWAP10");
			else write.println("SWAP10 non utilizzato");			
			if(this.SWAP11 > 0 ) write.println("SWAP11");
			else write.println("SWAP11 non utilizzato");
			if(this.SWAP12 > 0 ) write.println("SWAP12");
			else write.println("SWAP12 non utilizzato");
			if(this.SWAP13 > 0 ) write.println("SWAP13");
			else write.println("SWAP13 non utilizzato");
			if(this.SWAP14 > 0 ) write.println("SWAP14");
			else write.println("SWAP14 non utilizzato");
			if(this.SWAP15 > 0 ) write.println("SWAP15");
			else write.println("SWAP15 non utilizzato");
			if(this.SWAP16 > 0 ) write.println("SWAP16");
			else write.println("SWAP16 non utilizzato");
			if(this.LOG0 > 0 ) write.println("LOG0");
			else write.println("LOG0 non utilizzato");
			if(this.LOG1 > 0 ) write.println("LOG1");
			else write.println("LOG1 non utilizzato");	
			if(this.LOG2 > 0 ) write.println("LOG2");
			else write.println("LOG2 non utilizzato");
			if(this.LOG3 > 0 ) write.println("LOG3");
			else write.println("LOG3 non utilizzato");
			if(this.LOG4 > 0 ) write.println("LOG4");
			else write.println("LOG4 non utilizzato");
			if(this.CREATE > 0 ) write.println("CREATE");
			else write.println("CREATE non utilizzato");
			if(this.CALL > 0 ) write.println("CALL");
			else write.println("CALL non utilizzato");
			if(this.CALLCODE > 0 ) write.println("CALLCODE");
			else write.println("CALLCODE non utilizzato");
			if(this.RETURN > 0 ) write.println("RETURN");
			else write.println("RETURN non utilizzato");			
			if(this.DELEGATECALL > 0 ) write.println("DELEGATECALL");
			else write.println("DELEGATECALL non utilizzato");
			if(this.STATICCALL > 0 ) write.println("STATICCALL");
			else write.println("STATICCALL non utilizzato");			
			if(this.REVERT > 0 ) write.println("REVERT");
			else write.println("REVERT non utilizzato");
			if(this.INVALID > 0 ) write.println("INVALID");
			else write.println("INVALID non utilizzato");
			if(this.SELFDESTRUCT > 0 ) write.println("SELFDESTRUCT");
			else write.println("SELFDESTRUCT non utilizzato");
					
			write.close();
			files.clear();
	    }
	    
	    
	    public static void main(String[] args) throws Exception {
	    	/*
	    	 * Rinominare le directory contenenti i compilatori con solidity-develop-version
	    	 * e.g. solidity-develop-0.4.19
	    	 */
	    	ScannerSolidityCompiler scanner = new ScannerSolidityCompiler();
	    	// Modificare il path al proprio Desktop
	    	Path path = Paths.get("C:/Users/Kri/Desktop/Solidity-develop/");
	    	scanner.listSolidityCompilerVersion(path);
	    	
	    	Path path2;
	    	Iterator<Path> i = directory.iterator();
	    	while(i.hasNext()) {
	    		path2 = i.next();	 
	    		System.out.println(path2.toString());
		    	scanner.listFiles(path2);
		    	scanner.opcodeDeclaration();
		    	scanner.statistics();
		    	scanner.writeResults(path2.toString());
		    	// Reset degli attributi opcode
		    	scanner = new ScannerSolidityCompiler();
	    	}

	    	
	    }
}
