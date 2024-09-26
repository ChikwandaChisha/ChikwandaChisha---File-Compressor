//import java.io.*;
//import java.util.*;
//
//
///**
// * @ author Chipo Chibbamulilo
// * creating map for the pset3
// */
//public class Map3 {
//
//    /**
//     * Read file provided in pathName and count how many times each character appears
//     *
//     * @param pathName - path to a file to read
//     * @return - Map with a character as a key and the number of times the character appears in the file as value
//     * @throws IOException
//     */
//    public static Map<Character, Long> countFrequencies(String pathName) throws IOException {
//
//        //instantiating map
//        Map<Character, Long> charFrequencies = new TreeMap<>();
//        BufferedReader input = null;
//        try {
//            //opening file to be read
//            input = new BufferedReader(new FileReader(pathName));
//
//
//        } catch (FileNotFoundException e) {
//            System.err.println(" Cannot open file \n" + e.getMessage());
//        }
//
//        int c = 0;
//
//        while ((c= input.read()) != -1) { //this statement is actually important
//            char character = (char) c;
//
//            if (charFrequencies.containsKey(character)) {
//                Long frequency = charFrequencies.get(character);
//                frequency++;// increasing frequency by 1
//                charFrequencies.replace(character,frequency);
//
//            } else {
//                charFrequencies.put(character, 1L);
//            }
//        }
//        try{//closing the file
//            input.close();
//        }
//        catch (IOException e){
//            System.err.println(e.getMessage());
//        }
//        return charFrequencies;
//    }
//    public static BinaryTree<CodeTreeElement> makeCodeTree(Map<Character, Long> frequencies){
//        //making comparator object
//        Comparator<BinaryTree<CodeTreeElement>> compare=new CodeTree<>();
//
//        //instantiate queue
//        PriorityQueue<BinaryTree<CodeTreeElement>> treePq=new PriorityQueue<BinaryTree<CodeTreeElement>>(compare);
//
//        //looping through the map
//        for (Map.Entry<Character,Long> m :frequencies.entrySet()){
//            CodeTreeElement data=new CodeTreeElement(m.getValue(),m.getKey());
//            BinaryTree<CodeTreeElement> b=new BinaryTree<CodeTreeElement>(data,null,null);
////            System.out.println("------\n"+b);//testing
//
//            //add to priority queue
//            treePq.add(b);
//        }
////        System.out.println(treePq);// this is for testing
//
//        while (treePq.size()!=1){
//
//            BinaryTree<CodeTreeElement> T1=treePq.remove();
//            BinaryTree<CodeTreeElement> T2=treePq.remove();
//
//            //making frequency as data
//            CodeTreeElement resData= new CodeTreeElement(T1.data.getFrequency()+T2.data.getFrequency(),null);
//
//            //making an instance of BinaryTree class called T
//            BinaryTree<CodeTreeElement> T=new BinaryTree<>(resData,T1,T2);
//
//            //add to PriorityQueue
//            treePq.add(T);
//        }
//        //the only element present should be the code tree, don't forget to rewrite toString
//        return treePq.remove();
//    }
//
//    /**
//     * supposed to be a helper function for the compute code,
//     * I used the post order traverse to do so; so that I can do this
//     * @param codeTree
//     * @param mapCode
//     * @param code
//     * @return
//     */
//    public static void addToMap(BinaryTree<CodeTreeElement> codeTree, Map<Character, String> mapCode, String code){
//        //pre-order traversing
//        if(codeTree.isLeaf()) {
//            mapCode.put(codeTree.data.myChar,code);
//        }
//        else{
//            if(codeTree.hasRight()){
//                addToMap(codeTree.right,mapCode,code+"1");
//            }
//
//            if(codeTree.hasLeft()){
//                addToMap(codeTree.left,mapCode,code+"0");
//            }
//        }
//
//        // System.out.println(codeTree);
//    }
//
//    public static Map<Character, String> computeCodes(BinaryTree<CodeTreeElement> codeTree){
//        //call helper function add to Map
//        String code="";
//        Map<Character, String> mapCode=new TreeMap<>();
//        if (codeTree != null) addToMap(codeTree,mapCode,code);
//
//        System.out.println(mapCode);
//        return mapCode;
//    }
//    public static void compressFile(Map<Character, String> codeMap, String pathName, String compressedPathName) throws IOException{
//
//        //opening the files (input and output)
//        BufferedReader input=new BufferedReader(new FileReader(pathName));
//        BufferedBitWriter output=new BufferedBitWriter(compressedPathName);
//
//        int c;
//
//        while ((c= input.read()) != -1) {
//            char character = (char) c;
//            String bit=(codeMap.get(character));
//
//            for(char i: bit.toCharArray()){
//                System.out.println(i);
//                output.writeBit(i == '1');
//            }
//        }
//        // closing both files;
//        input.close();
//        output.close();
//
//    }
//
//    /**
//     * Decompress file compressedPathName and store plain text in decompressedPathName.
//     * @param compressedPathName - file created by compressFile
//     * @param decompressedPathName - store the decompressed text in this file, contents should match the original file before compressFile
//     * @param codeTree - Tree mapping compressed data to characters
//     * @throws IOException
//     */
//    public static void decompressFile(String compressedPathName, String decompressedPathName, BinaryTree<CodeTreeElement> codeTree) throws IOException{
//
//        //opening the files
//        BufferedBitReader bitInput=new BufferedBitReader(compressedPathName);
//        BufferedWriter output= new BufferedWriter(new FileWriter(decompressedPathName));
//
//        //making a pointer for the tree
//        BinaryTree<CodeTreeElement> curr=codeTree;
//
//        while (bitInput.hasNext()) {
//            boolean bit = bitInput.readBit();
//            // System.out.println(bit);
//
//            // do something with bit
//            //if we're at the leaf, we write the char found onto new document
//            if(curr.isLeaf()){
//                output.write(curr.data.myChar);
//                // System.out.println(curr.data.myChar);//debugging statement
//                curr=codeTree;
//            }
//
//            else{
//                if(bit){
//                    curr=curr.right;
//                }
//                else{
//                    curr=curr.left;
//                }
//            }
//
//        }
//        //important statement so that it writes
//        output.close();
//        bitInput.close();
//    }
//
//
//    public static void main(String[] args) throws IOException {
//        Map<Character, Long> map= countFrequencies("pset3/SampleFile");
//        // System.out.println(makeCodeTree(map));
//        Map<Character, String> mapcode=computeCodes(makeCodeTree(map));
//        //System.out.println(mapcode);
//        compressFile(mapcode,"pset3/SampleFile","pset3/compressedfile.txt");
//        decompressFile("pset3/compressedfile.txt","pset3/decompressedfile.txt",makeCodeTree(map));
//
//
//    }
//}
