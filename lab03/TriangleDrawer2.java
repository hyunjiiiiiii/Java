public class TriangleDrawer2 {
    public TriangleDrawer2() {
    }

    public static void main(String[] args) {
        int Size = 10;

        for(int row = 0; row < Size;) {
            row += 1;
            for(int col = 0; row > col;) {
                col += 1;
                System.out.print('*');
            }
            System.out.println();
        }
    }
}
