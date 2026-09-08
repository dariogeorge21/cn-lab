
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class server{
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(9002);
        System.out.println("Server waiting for the request!!");

        Socket socket = server.accept();
        System.out.println("Client is connected!");

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        
        int n = in.readInt();

        int[][] matrix = (int[][])in.readObject();

        System.out.println("Recieved Matrix: ");

        // Log the matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }

        boolean upper = true;
        boolean lower = true;
        boolean diagonal = true;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                // Non-zero below diagonal → NOT upper
                if (i > j && matrix[i][j] != 0) {
                    upper = false;
                }

                // Non-zero above diagonal → NOT lower
                if (i < j && matrix[i][j] != 0) {
                    lower = false;
                }

                // Non-zero outside diagonal → NOT diagonal
                if (i != j && matrix[i][j] != 0) {
                    diagonal = false;
                }
            }
        }


        String result = new String();

        System.out.print("\nMatrix Type: ");

        if (diagonal) {
            result = "Diagonal Matrix";
        } else if (upper){
            result = "Upper Triangular Matrix";
        } else if (lower){
            result = "Lower Triangular Matrix";
        } else{
            result = "Normal Matrix";
        }

        System.out.println(result);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF(result);

        socket.close();
        server.close();
    } 
}