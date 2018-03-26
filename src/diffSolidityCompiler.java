import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class diffSolidityCompiler {

	private static List<String> s = new ArrayList<String>();
	ProcessBuilder pb = new ProcessBuilder(s);
	
	public void doGitDiff() throws IOException, InterruptedException {

    	File file = new File("C:/Users/Kri/Desktop/solidity/tags.txt");
    	if(file.exists()) {
    	BufferedReader br = new BufferedReader(new FileReader(file));
    	String line;
    	int count = 2;
    	int count2 = 1;
    	int countapp = 0;
    	int count2app = 0;
    	String diff1 = null;
    	String diff2 = null;
    	String app = null;
    	boolean flag = false;
    	boolean flag2 = false;
    	while ((line = br.readLine()) != null && count2 <= 4) {
            s.clear();
            s.add("cmd.exe");
            s.add("/C");
    		System.out.println("count2 "+count2+"count "+count);
    		if(line.contains("0."+count2+"."+count) == true) {
    			diff1 = line.substring(0, 7);
    			count++;
    		}
    		else {   			
    			if(line.contains("untagged") == false) {
    				count = 0;
    				count2++;
    				System.out.println("count2 "+count2+"count "+count);
    				if(line.contains("0."+count2+"."+count) == true) {
    	    			diff1 = line.substring(0, 7);
    	    			count++;
    	    		}
        			countapp = count;
        			count2app = count2;
    			}
    		}
    		if(count == 3) app = diff1;
    		if(count == 4) {
        	//	System.out.println("diff1 "+ diff1);
        	//	System.out.println("app "+ app);
    			s.add("git diff "+app+" "+diff1+" >> diffv.0.1.2-v.0.1.3.txt");
    			flag = true;
    			diff2 = diff1;
    			countapp = count;
    			count2app = count2;
    		}
    		else if(flag == true){
    			
        		//System.out.println("diff2 "+ diff2);
        		//System.out.println("diff1 "+ diff1);
    			s.add("git diff "+diff2+" "+diff1+" >> diffv.0."+count2app+"."+countapp+"-v.0."+count2+"."+count+".txt"); 
    			diff2 = diff1;
    			countapp = count;
    			count2app = count2;
    		}
            pb = new ProcessBuilder(s);
            pb.directory(new File("C:/Users/Kri/Desktop/solidity"));
            pb.redirectErrorStream(true);
            pb.redirectInput(ProcessBuilder.Redirect.INHERIT);
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            Process p = pb.start();
            p.waitFor(); // Attendo finchè non è stato creato il file tags.txt
    	}
	

    }
    	else System.out.println("no file");
	}
	
	public void createTags() throws Exception {
		s.add("cmd.exe");
    	s.add("/C");
    	s.add("git show-ref --abbrev=7 --tags >> tags.txt");
    	//s.add("git diff f0d539a 68ef581 >> diffv.0.4.10-v.0.4.11.txt");
        pb = new ProcessBuilder(s);
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
        d.doGitDiff();
        

    }
}