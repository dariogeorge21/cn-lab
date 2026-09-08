import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class client{
    public static void main(String[] args) throws Exception{
        // initialise the scanner class
        Scanner sc = new Scanner(System.in);

        // initialise the random class
        Random rand = new Random();

        // collect the order of matrix
        System.out.println("Enter the order of matrix: ");

        int n = sc.nextInt();

        int[][] matrix = new int[n][n]; 

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = rand.nextInt(50)+1;
            }
        }

        // type of matrix using random
        int typeOfMatrix = rand.nextInt(4);

        if (typeOfMatrix == 0){
            // upper triangular
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i>j){
                        matrix[i][j] = 0;
                    }   
                }
            }
        } else if (typeOfMatrix == 1) {
            // lower triangular matrix
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i<j){
                        matrix[i][j] = 0;
                    }   
                }
            }  
        } else if (typeOfMatrix == 2) {
            // Diagonal Matrix
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i!=j){
                        matrix[i][j] = 0;
                    }   
                }
            }  
        }

        System.out.println("Generated matrix: ");
        
        // Log the matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }

        // Send the data

        // initialise the socket
        try {
            Socket socket = new Socket("localhost",9002);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            out.writeInt(n);
            out.writeObject(matrix);
            out.flush();

            String result = in.readUTF();
            System.out.println("\nMatrix Type: "+ result);

            socket.close();

        } catch (Exception e) {
            System.out.println("ERROR in creating socket!");
        }
        
    }
}