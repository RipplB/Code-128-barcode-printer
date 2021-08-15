import java.awt.print.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Scanner;

public class VonalkodNyomtato implements Printable {
    static final int MODULE = 2; 
    static LinkedList<String> strings = new LinkedList<String>();
    static Scanner input = new Scanner(System.in);
    PrinterJob job;

    static final String[]values = {
        "11011001100",   "11001101100",   "11001100110", 
        "10010011000",   "10010001100",   "10001001100",
        "10011001000",   "10011000100",   "10001100100",
        "11001001000",   "11001000100",   "11000100100",
        "10110011100",   "10011011100",   "10011001110",
        "10111001100",   "10011101100",   "10011100110",
        "11001110010",   "11001011100",   "11001001110",
        "11011100100",   "11001110100",   "11101101110",
        "11101001100",   "11100101100",   "11100100110",
        "11101100100",   "11100110100",   "11100110010",
        "11011011000",   "11011000110",   "11000110110",
        "10100011000",   "10001011000",   "10001000110",
        "10110001000",   "10001101000",   "10001100010",
        "11010001000",   "11000101000",   "11000100010",
        "10110111000",   "10110001110",   "10001101110",
        "10111011000",   "10111000110",   "10001110110",
        "11101110110",   "11010001110",   "11000101110",
        "11011101000",   "11011100010",   "11011101110",
        "11101011000",   "11101000110",   "11100010110",
        "11101101000",   "11101100010",   "11100011010",
        "11101111010",   "11001000010",   "11110001010",
        "10100110000",   "10100001100",   "10010110000",
        "10010000110",   "10000101100",   "10000100110",
        "10110010000",   "10110000100",   "10011010000",
        "10011000010",   "10000110100",   "10000110010",
        "11000010010",   "11001010000",   "11110111010",
        "11000010100",   "10001111010",   "10100111100",
        "10010111100",   "10010011110",   "10111100100",
        "10011110100",   "10011110010",   "11110100100",
        "11110010100",   "11110010010",   "11011011110",
        "11011110110",   "11110110110",   "10101111000",
        "10100011110",   "10001011110",   "10111101000",
        "10111100010",   "11110101000",   "11110100010",
        "10111011110",   "10111101110",   "11101011110",
        "11110101110",   "11010000100",   "11010010000",
        "11010011100",   "11000111010"
    };
    static final char[] charSetB = {
        ' ' , '!' , '"' , '#' , '$' , '%' , '&' ,'\'' ,
        '(' , ')' , '*' , '+' , ',' , '-' , '.' , '/' ,
        '0' , '1' , '2' , '3' , '4' , '5' , '6' , '7' ,
        '8' , '9' , ':' , ';' , '<' , '=' , '>' , '?' ,
        '@' , 'A' , 'B' , 'C' , 'D' , 'E' , 'F' , 'G' ,
        'H' , 'I' , 'J' , 'K' , 'L' , 'M' , 'N' , 'O' ,
        'P' , 'Q' , 'R' , 'S' , 'T' , 'U' , 'V' , 'W' ,
        'X' , 'Y' , 'Z' , '[' , '\\' , ']' , '^' , '_' ,
        '`' , 'a' , 'b' , 'c' , 'd' , 'e' , 'f' , 'g' ,
        'h' , 'i' , 'j' , 'k' , 'l' , 'm' , 'n' , 'o' ,
        'p' , 'q' , 'r' , 's' , 't' , 'u' , 'v' , 'w' ,
        'x' , 'y' , 'z' , '{' , '|' , '}' , '~'
    };

    public static String encodeAsB(String stringToEncode){
        String encoded = values[104];
        int sum = 104;
        
        top: for (int t = 0; t < stringToEncode.length(); t++) {
            for (int i = 0; i < charSetB.length; i++) {
                if (stringToEncode.charAt(t) == charSetB[i]) {
                    encoded = encoded + values[i];
                    sum += (t + 1) *i;
                    continue top;
                }
            }
        }
        encoded = encoded + values[sum % 103];
        encoded = encoded + "1100011101011";

        return encoded;
    }


    public VonalkodNyomtato() {
        this.job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        final boolean ok = job.printDialog();
        if (ok) {
            try {
                job.print();
            } catch (final PrinterException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int print(final Graphics graph, final PageFormat format, final int page) {
        if (page > 0) {
            return NO_SUCH_PAGE;
        }
        final Graphics2D g2d = (Graphics2D) graph;
        g2d.translate(format.getImageableX(), format.getImageableY());
        int yOffset = 10;
        for (final String string : strings) {
            String encoded = encodeAsB(string);
            int xOffset = (int) format.getWidth() / 2 - encoded.length();
            for (int i = 0; i < encoded.length(); i++) {
                if(encoded.charAt(i) == '0'){
                    continue;
                }
                g2d.fillRect(xOffset + i * MODULE, yOffset, MODULE, 72);
            }
            graph.drawString(string, (int) (format.getWidth() / 2 - string.length() *2), yOffset + 82);

            yOffset += 90;
        }
        return PAGE_EXISTS;
    }

    public static void main(final String[] args) {
        System.out.println("Csak ird be ami kell. Ures sor inditja a nyomtatast");
        while (true) {
            final String next = input.nextLine();
            if (next.equals("")) {
                new VonalkodNyomtato();
                strings.clear();
                continue;
            }
            strings.add(next);
        }
    }
}