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

        s.close();

        this.quicksort(this.participants, 0, this.participants.size());
    }

    public void printParticipants() {
        System.out.println("--------------");

        int i;
        for (i = 0; i < this.numberOfParticipants; i++) {
            Participant p = participants.get(i);
            System.out.println(p.age + " " + p.name);
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

    // public int partition(List<Participant> A, int p, int r) {
    // Participant x = A.get(r - 1);
    // int i = p - 1;
    // int j;
    // for (j = p; j < r; j++) {
    // if (A.get(j).age <= x.age) {
    // i = i + 1;
    // this.swap(A, i, j);
    // }
    // }
    // return (i + 1);
    // }

    public int partition(List<Participant> A, int p, int r) {
        int meio = (p + r - 1) / 2;
        Participant a = A.get(p);
        Participant b = A.get(meio);
        Participant c = A.get(r - 1);
        int medianaIndice = 0;

        if (a.age < b.age) {
            if (b.age < c.age) {
                medianaIndice = meio;
            }

            else {
                if (a.age < c.age)
                    medianaIndice = r - 1;
                else
                    medianaIndice = p;
            }
        }

        else {
            if (c.age < b.age)
                medianaIndice = meio;

            else {
                if (c.age < a.age)
                    medianaIndice = r - 1;

                else
                    medianaIndice = p;
            }
        }
        this.swap(A, medianaIndice, r - 1);

        Participant x = A.get(r - 1);
        int i = p - 1;
        int j;
        for (j = p; j < r - 1; j++) {
            if (A.get(j).age <= x.age) {
                i = i + 1;
                this.swap(A, i, j);
            }
        }
        this.swap(A, i + 1, r - 1);
        return (i + 1);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.setParticipants();
        main.printParticipants();
    }
}