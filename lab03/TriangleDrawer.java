public class TriangleDrawer {

    public TriangleDrawer() {
    }

    public static void main(String[] args) {
        int row = 0;
        int Size = 10;

        while(row < Size) {
            row += 1;
            int col = 0;
            while(row > col) {
                col += 1;
                System.out.print('*');
            }
            System.out.println();
        }
    }
}
