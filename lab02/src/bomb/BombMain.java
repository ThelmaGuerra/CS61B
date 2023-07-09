package bomb;

import common.IntList;

public class BombMain {
    public static void main(String[] args) {
        int phase = 2;
        if (args.length > 0) {
            phase = Integer.parseInt(args[0]);
        }
        // TODO: Find the correct passwords to each phase using debugging techniques
        Bomb b = new Bomb();

        StringBuilder password2 = new StringBuilder();

        for(int i = 0; i < 1338; i++) {
            if(i == 1337) {
                password2.append(Integer.toString(-81201430)).append(" ");
                continue;
            }
            password2.append(Integer.toString(i)).append(" ");
        }

        if (phase >= 0) {
            b.phase0("39291226");
        }
        if (phase >= 1) {
            b.phase1(IntList.of(0,9,3,0,8)); // Figure this out too
        }
        if (phase >= 2) {
            b.phase2(password2.toString());
        }
    }
}
