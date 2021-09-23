import java.io.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class kkpkkp {
    public static void main(String[] args) {
        //define filename
        String file = "C:\\Users\\Lenovo\\IdeaProjects\\kkp\\src\\sbssbsb.c";

        //define keywords array
        String keyWords[] = {"auto", "break", "case", "char", "const", "continue", "default"
                , "do", "double", "else", "enum", "extern", "float", "for", "goto", "if"
                , "int", "long", "register", "return", "short", "signed", "sizeof", "static"
                , "struct", "switch", "typedef", "union", "unsigned", "void", "volatile", "while"};


        //read the file by line
        String str[]=new String[1024];
        int rowNum = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while((str[rowNum] = reader.readLine())!=null){
                rowNum++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //eliminate interruptions using regular expression
        String totalstr = "";
        boolean sb = false;
        for (int i = 0;i < rowNum;i++) {
            str[i] = str[i].replaceAll("\\s*/{2}.*","")  ;
            str[i] = str[i].replaceAll("\".*\"","");
            if(str[i].startsWith("#")) str[i]="";
            totalstr += str[i];
        }
        totalstr=totalstr.replaceAll("/\\*.*\\*/","");


        //after eliminating interference, count the number of keywords
        //remember that "do" and "double" may repeat, minus 1 when meet "double"
        int totalkw = 0,countswitch = 0,countcase=0,countdouble=0;

            for (int i=0;i<rowNum;i++){
                countcase+=countkw("case",str[i]);
                countswitch+=countkw("switch",str[i]);
                countdouble+=countkw("double",str[i]);
                for (int j=0;j<keyWords.length;j++){
                    totalkw += countkw(keyWords[j], str[i]);
                }
            }
            totalkw-=countdouble;
        System.out.println("total num: "+totalkw);

        //count switch can cases in each parts
        System.out.println("switch num: "+countswitch);
        System.out.print("case num: ");
        for (int i=0;i<rowNum;i++){
            if(countswitch==1){
                System.out.println(countcase);
                break;
            }else{
                if(str[i].contains("switch")){
                    countswitch--;
                    int casenum =0;
                    for (int j=i+1;j<rowNum;j++){
                        if(str[j].contains("case")){
                            casenum++;
                            countcase--;
                        }
                        if(str[j].contains("switch")){
                            System.out.print(casenum+" ");
                            break;
                        }
                    }
                }
            }
        }


        //count ifelse, ifelseif
        Vector<Integer> v = new Vector<>();
        for(int i=0;i<rowNum;i++){
            if(str[i].contains("else if")){
                v.add(2);
            }else if(str[i].contains("if")){
                v.add(1);
            }else if(str[i].contains("else")){
                v.add(-1);
            }
        }

        int cifelse = 0;
        int cifelseif = 0;
        for (int i=1;i<v.size()-1;i++){
            if(v.elementAt(i)==2&&v.elementAt(i-1)==2){
                v.remove(i);
            }
        }
        for (int i=0;i<v.size()-1;i++){
            if(v.elementAt(i)==1&&v.elementAt(i+1)==-1){
                v.remove(i);
                v.remove(i);
                cifelse++;
            }
            if(i>=v.size())
                break;
            if(v.elementAt(i)==1&&v.elementAt(i+1)==2){
                v.remove(i);
            }
        }

        while(true) {
            for (int i = 0; i < v.size()-1; i++) {
                if (v.elementAt(i) == 2 && v.elementAt(i + 1) == -1) {
                    v.remove(i);
                    v.remove(i);
                    cifelseif++;
                }
            }
            if(v.size()==1)
                break;
        }
        System.out.println("if-else num: "+cifelse);
        System.out.println("if-elseif-else num: "+cifelse);

    }

    //count the number of keywords use regular expression
    static public int countkw(String kw,String str){
        Pattern p = Pattern.compile(kw,Pattern.CASE_INSENSITIVE);
        Matcher m =p.matcher(str);
        int count = 0;
        while(m.find()){
            count++;
        }
        return count;
    }

    static public void ifelsecount(String str,int elsenum,int elseifnum){
        Stack<String> s = new Stack<>();

    }
}

