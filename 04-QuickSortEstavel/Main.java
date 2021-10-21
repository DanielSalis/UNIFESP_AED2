import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public class Participant {
        int id;
        int firstPosition;
        int lastPosition;
        int age;
        String name;
    }

    int positionToPrint;
    int numberOfPrints;
    int numberOfParticipants;
    List<Participant> participants = new ArrayList<Participant>();

    public void setParticipants() {
        Scanner s = new Scanner(System.in);
        this.numberOfParticipants = s.nextInt();

        int i;
        for (i = 0; i < numberOfParticipants; i++) {
            Participant p = new Participant();
            p.id = i;
            p.firstPosition = i;
            p.lastPosition = -1;
            p.name = s.next();
            p.age = s.nextInt();
            this.participants.add(p);
        }

        this.positionToPrint = s.nextInt();
        this.numberOfPrints = s.nextInt();

        s.close();

        this.quicksort(this.participants, 0, this.participants.size() - 1);
    }

    public void printParticipants() {
        int i;
        for (i = 0; i < this.numberOfPrints; i++) {
            Participant p = participants.get(i + positionToPrint - 1);
            System.out.println(p.name + " " + p.age);
        }
    }

    public void swap(List<Participant> A, int i, int j) {
        Participant I = A.get(i);
        Participant J = A.get(j);

        A.set(i, J);
        J.lastPosition = i;

        A.set(j, I);
        I.lastPosition = j;
    }

    public void quicksort(List<Participant> A, int p, int r) {
        if (p < r) {
            int q = this.partition(A, p, r);
            quicksort(A, p, q - 1);
            quicksort(A, q + 1, r);
        }
    }

    public int partition(List<Participant> A, int p, int r) {
        int meio = (p + r) / 2;
        Participant a = A.get(p);
        Participant b = A.get(meio);
        Participant c = A.get(r);
        int mediana = 0;

        if (a.age < b.age) {
            if (b.age < c.age) {
                mediana = meio;
            }

            else {
                if (a.age < c.age)
                    mediana = r;
                else
                    mediana = p;
            }
        }

        else {
            if (c.age < b.age)
                mediana = meio;

            else {
                if (c.age < a.age)
                    mediana = r;

                else
                    mediana = p;
            }
        }
        this.swap(A, mediana, r);

        Participant x = A.get(r);
        int i = p - 1;
        int j;
        for (j = p; j <= r - 1; j++) {
            if (A.get(j).age <= x.age) {
                i = i + 1;
                this.swap(A, i, j);
            }
        }
        this.swap(A, i + 1, r);
        return (i + 1);
    }

    public void isStable() {
        boolean stable = this.verifyOrder();
        if (stable) {
            System.out.println("yes");
        } else {
            System.out.println("no");
        }
    }

    public boolean verifyOrder() {
        int i;

        if (this.participants.size() > 1) {
            for (i = 0; i < this.participants.size() - 2; i++) {
                Participant p1 = participants.get(i);
                Participant p2 = participants.get(i + 1);

                if (p1.age == p2.age) {
                    if ((p1.firstPosition < p2.firstPosition) && (p1.lastPosition < p2.lastPosition)) {
                        continue;
                    } else {
                        return false;
                    }
                }
            }
            return true;
        }
        return true;
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.setParticipants();
        main.isStable();
        main.printParticipants();
    }
}