import java.util.Scanner;

public class SudokuSolver {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Die Schleife wird fortgesetzt, solange ein Wert n eingelesen wird.
        while (sc.hasNextInt()) {
            int n = sc.nextInt();
            int size = n * n; // Gesamtlänge der Gitterseite
            int[][] board = new int[size][size];

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (sc.hasNextInt()) {
                        board[i][j] = sc.nextInt();
                    }
                }
            }

            // Start des Algorithmus zur Lösung
            if (solve(board, n)) {
                printBoard(board);
            } else {
                printNoSolution();
            }
        }
        sc.close();
    }

    /**
     * Um die LEXIKOGRAPHISCHE Reihenfolge zu garantieren, gehen wir wie folgt vor:
     * 1. Die Zellen werden strikt von links nach rechts und von oben nach unten durchlaufen.
     * 2. Die möglichen Zahlen (num) werden in aufsteigender Reihenfolge von 1 bis size ausprobiert.
     */
    private static boolean solve(int[][] board, int n) {
        int size = n * n;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                // Suche nach der ersten leeren Zelle
                if (board[row][col] == 0) {
                    for (int num = 1; num <= size; num++) {
                        // Falls die Zahl den Sudoku-Regeln entspricht
                        if (isValid(board, row, col, num, n)) {
                            board[row][col] = num; // Testeintrag

                            // Rekursiver Versuch, die Lösung fortzusetzen.
                            if (solve(board, n)) {
                                return true; // Falls eine Lösung gefunden wird, wird true zurückgegeben.
                            }
                            /* Backtracking: Wenn keine der Zahlen zum Erfolg führt, wird die Zelle
                            zurückgesetzt (auf 0), um einen anderen Weg in der Rekursion zu probieren.*/
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Überprüfung der drei Sudoku-Regeln:
     * 1. Eindeutigkeit in der Zeile.
     * 2. Eindeutigkeit in der Spalte.
     * 3. Eindeutigkeit im n×n-Block.
     */
    private static boolean isValid(int[][] board, int row, int col, int num, int n) {
        int size = n * n;

        //Anfangskoordinaten des n×n-Teilblocks, zu dem die Zelle (row, col) gehört
        //Zeilenindex - Row
        //Spaltenindex - Col
        int startRow = (row / n) * n;
        int startCol = (col / n) * n;

        for (int i = 0; i < size; i++) {
            // Zeilenprüfung
            if (board[row][i] == num) return false;

            // Spaltenprüfung
            if (board[i][col] == num) return false;

            // Prüfung des n×n-Blocks
            // Die Formel (i / n) gibt die Verschiebung in der Zeile an, (i % n) – in der Spalte.
            if (board[startRow + (i / n)][startCol + (i % n)] == num) return false;
        }
        return true;
    }

    /**
     * Gibt das Gitter aus.
     */
    private static void printBoard(int[][] board) {
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            StringBuilder output = new StringBuilder();
            for (int j = 0; j < board[i].length; j++) {
                output.append(board[i][j]);
                if (j < board[i].length - 1) {
                    output.append(" ");
                }
            }
            System.out.println(output);
        }
        System.out.println();
    }

    private static void printNoSolution() {
        System.out.println();
        System.out.println("NO SOLUTION");
        System.out.println();
    }
}

