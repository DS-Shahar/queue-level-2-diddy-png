import java.util.Scanner;

class Main {
    public static void main(String[] args) {

        Queue<Integer> q = new Queue<>();
        q.insert(5);
        q.insert(7);
        q.insert(8);
        q.insert(9);
        q.insert(10);
        System.out.println("Enter numbers for q1 (type -1 to stop):");
        Queue<Integer> q1 = createqueue();
        System.out.printf("%s | %s\n", q1, q);
        System.out.printf("ex 1 : %s \n", ex_1(q));
        System.out.printf("ex 2 : %s \n", ex_2(q));
        System.out.printf("ex 3 : %s \n", ex_3(q, 35));
        System.out.printf("ex 4 : %s \n", ex_4(q1, q));
        System.out.printf("%s | %s\n", q1, q);
        System.out.printf("ex 5 : %s \n", ex_5(q1, 4));
        System.out.println("----------------------------------------------------------------------------------");
        Queue<Character> q2 = new Queue<>();
        q2.insert('c');
        q2.insert('c');
        q2.insert('a');
        q2.insert('c');
        System.out.printf("ex_2_1 : %s \n", ex_2_1(q2));
        System.out.printf("ex_2_5 : %s \n", ex_2_5(q, q1));
        System.out.printf("ex_2_6 : %s \n", ex_2_6(q1));
        System.out.println(numInPlace(7720, 2));
        radixSort(q1);
        System.out.printf("Radix sort : %s", q1);

    }

    @SuppressWarnings("ConvertToTryWithResources")
    public static Queue<Integer> createqueue() {
        Queue<Integer> q = new Queue<>();
        Scanner rd = new Scanner(System.in);

        if (rd.hasNextInt()) {
            int x = rd.nextInt();
            while (x != -1) {
                q.insert(x);
                x = rd.nextInt();
            }
        }
        rd.close();
        return q;
    }

    public static <T> Queue<T> ex_1(Queue<T> q) {
        Queue<T> co = new Queue<>();
        Queue<T> te = new Queue<>();
        while (!q.isEmpty()) {
            T vl = q.remove();
            co.insert(vl);
            te.insert(vl);
        }
        while (!te.isEmpty())
            q.insert(te.remove());
        return co;
    }

    public static double ex_2(Queue<Integer> q1) {
        Queue<Integer> q2 = ex_1(q1);
        double sum = 0;
        double i = 0;
        while (!q2.isEmpty()) {
            sum += q2.remove();
            i++;
        }
        if (i == 0)
            return 0;
        return sum / i;
    }

    public static int ex_3(Queue<Integer> q1, int num) {
        int x = 0;
        Queue<Integer> q2 = ex_1(q1);
        while (!q2.isEmpty()) {
            if (num % q2.remove() == 0)
                x++;
        }
        return x;
    }

    public static boolean ex_4(Queue<Integer> q1, Queue<Integer> q2) {
        Queue<Integer> q3 = ex_1(q1);
        while (!q3.isEmpty()) {
            if (ex_3(q2, q3.remove()) == 0)
                return false;
        }
        return true;
    }

    public static <T> boolean ex_5(Queue<T> q1, T x) {
        Queue<T> q2 = ex_1(q1);
        if (q2.isEmpty())
            return false;
        T prev = q2.remove();
        while (!q2.isEmpty()) {
            T now = q2.remove();
            if (prev.equals(now) && now.equals(x))
                return true;
            prev = now;
        }
        return false;
    }

    // ----------------------------------------------------------------------------------------------------------------------
    public static <T> Queue<Integer> ex_2_1(Queue<T> cq) { // O(n)-גודל הקלט זה אורך התור
        Queue<Integer> res = new Queue<>();
        Queue<T> copy = ex_1(cq);
        if (copy.isEmpty())
            return res;
        T prev = copy.remove();
        int count = 1;
        while (!copy.isEmpty()) {
            T now = copy.remove();
            if (now == prev)
                count++;
            else {
                prev = now;
                res.insert(count);
                count = 1;
            }
        }
        res.insert(count);
        return res;
    }

    public static Queue<Integer> ex_2_5(Queue<Integer> q1, Queue<Integer> q2) {
        Queue<Integer> copy = ex_1(q1);
        Queue<Integer> copy2 = ex_1(q2);
        Queue<Integer> res = new Queue<>();
        while (!copy.isEmpty() && !copy2.isEmpty()) {
            if (copy.head() <= copy2.head())
                res.insert(copy.remove());
            else
                res.insert(copy2.remove());
        }
        while (!copy.isEmpty())
            res.insert(copy.remove());
        while (!copy2.isEmpty())
            res.insert(copy2.remove());
        return res;
    }

    public static int ex_2_6(Queue<Integer> q1) {
        Queue<Integer> copy = ex_1(q1);
        int count = 0;
        int max_count = 0;
        int sum = 0;
        int max_sum = 0;
        while (!copy.isEmpty()) {
            int now = copy.remove();
            if (now % 2 == 0) {
                count++;
                sum += now;
            } else {
                if (count > max_count) {
                    max_count = count;
                    max_sum = sum;
                }
                count = 0;
                sum = 0;
            }
        }
        if (count > max_count)
            max_sum = sum;
        return max_sum;
    }

    public static int max(Queue<Integer> q1) {
        Queue<Integer> copy = ex_1(q1);
        int num = 0;
        if (!copy.isEmpty())
            num = copy.remove();
        while (!copy.isEmpty()) {
            num = Math.max(copy.remove(), num);
        }
        return num;
    }

    public static void radixSort(Queue<Integer> q) {
        if (q == null || q.isEmpty())
            return;

        @SuppressWarnings("unchecked")
        Queue<Integer>[] buckets = (Queue<Integer>[]) new Queue[10];
        for (int i = 0; i < 10; i++)
            buckets[i] = new Queue<>();
        int maxVal = max(q);
        int maxDigits = digits(maxVal);
        for (int d = 0; d < maxDigits; d++) {
            while (!q.isEmpty()) {
                int num = q.remove();
                int digit = numInPlace(num, d);
                buckets[digit].insert(num);
            }
            for (int i = 0; i < 10; i++) {
                while (!buckets[i].isEmpty())
                    q.insert(buckets[i].remove());
            }
        }
    }

    public static int digits(int x) {
        return (int) Math.log10(x) + 1;
    }

    public static int numInPlace(int num, int i) {
        String s = Integer.toString(num);
        if (s.length() <= i)
            return 0;
        return Character.getNumericValue(s.charAt(s.length() - i - 1));
    }
}
