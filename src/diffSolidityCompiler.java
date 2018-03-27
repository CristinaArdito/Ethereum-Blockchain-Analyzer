import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Al fine di ottenere un corretto funzionamento della classe è 
 * necessario che sia installato Git : https://gitforwindows.org/
 * 
 */

public class diffSolidityCompiler {

	private static List<String> s = new ArrayList<String>();
	ProcessBuilder pb = new ProcessBuilder(s);
	
	/**
	 * Riordina le versioni presenti sul file in modo crescente
	 * @param filePath - path del file
	 */
	public void reorderFile(String filePath) {

		List<Node> list = new ArrayList<Node>();
		List<String> untagged = new ArrayList<String>();
		String buffer = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
			do {
				buffer = reader.readLine();
				if (buffer != null) {
					if (buffer.contains("untagged")) {
						untagged.add(buffer);
					} else {
						list.add(new Node(buffer));
					}
				}
			} while (buffer != null);
			reader.close();
			Collections.sort(list);
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePath)));
			for(String item : untagged) {
				writer.write(item);
				writer.newLine();
			}
			for(Node item : list) {
				writer.write(item.getValue());
				writer.newLine();
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Effettua i git diff tra le varie versioni e sottoversioni del compilatore realizzato 
	 * al fine di ottenere le modifiche al codice che sono state effettuate
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void doGitDiff() throws IOException, InterruptedException {

        // Modificare percorso alla cartella contenente il compilatore Solidity
    	File file = new File("C:/Users/Kri/Desktop/solidity/tags.txt");
    	if(file.exists()) {
    	BufferedReader br = new BufferedReader(new FileReader(file));
    	String line;
    	int count = 0;
    	String app = null;
    	while ((line = br.readLine()) != null) {
            s.clear();
            s.add("cmd.exe");
            s.add("/C");            
            count++;
    		if((count > 1) && (!app.contains("untagged")) && (!line.contains("untagged"))) {
    			s.add("git diff "+app.substring(0, 7)+" "+line.substring(0, 7)+" >> diff"+app.substring(18, app.length())+"-v"+line.substring(18, line.length())+".txt"); 
	            pb = new ProcessBuilder(s);
	            pb.directory(new File("C:/Users/Kri/Desktop/solidity"));
	            pb.redirectErrorStream(true);
	            pb.redirectInput(ProcessBuilder.Redirect.INHERIT);
	            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
	            Process p = pb.start();
	            p.waitFor();
    		}
    		app = line;
    	}
    	br.close();
        }
    	else {
    		System.out.println("È necessario generare il file tags.txt.");
    	}
	}
		
	/**
	 * Crea un file contenente tutte le tags del compilatore 
	 * @throws Exception
	 */
	public void createTags() throws Exception {
		s.add("cmd.exe");
    	s.add("/C");
    	s.add("git show-ref --abbrev=7 --tags >> tags.txt");
        pb = new ProcessBuilder(s);
        // Modificare percorso alla cartella contenente il compilatore Solidity
        pb.directory(new File("C:/Users/Kri/Desktop/solidity"));
        pb.redirectErrorStream(true);
        pb.redirectInput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        Process p = pb.start();
        p.waitFor(); // Attendo finchè non è stato creato il file tags.txt		
	}
	
    public static void main(String[] args) throws Exception {
   	
        diffSolidityCompiler d = new diffSolidityCompiler();
        d.createTags();
        // Modificare percorso alla cartella contenente il compilatore Solidity
        d.reorderFile("C:/Users/Kri/Desktop/solidity/tags.txt");
        d.doGitDiff();
    }
    
	private class Node implements Comparable<Node>{
		
		private String value;
		
		public Node(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return this.value;
		}
		
		public int compareTo(Node o) {
			int numSeq1[] = new int[3], numSeq2[] = new int[3];
			String[] split = new String[3];
			String nSeq = value.substring(value.lastIndexOf("/v")+2, value.length());
			split = nSeq.split("\\.");
			for(int i=0; i<3;i++) {
				numSeq1[i] = Integer.parseInt(split[i]);
			}
			nSeq = o.getValue().substring(o.getValue().lastIndexOf("/v")+2, o.getValue().length());
			split = nSeq.split("\\.");
			for(int i=0; i<3;i++) {
				numSeq2[i] = Integer.parseInt(split[i]);
			}
			
			if(numSeq1[0] > numSeq2[0]) return 1;
			else if(numSeq1[0] < numSeq2[0]) return -1;
			else if(numSeq1[1] > numSeq2[1]) return 1;
			else if(numSeq1[1] < numSeq2[1]) return -1;
			else if(numSeq1[2] > numSeq2[2]) return 1;
			else if(numSeq1[2] < numSeq2[2]) return -1;
			else return 0;
		}
		
		public String toString() {
			return value;
		}
	}
	
}

