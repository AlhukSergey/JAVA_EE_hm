package by.teachmeskills.bridge;

import java.util.ArrayList;
import java.util.List;

public class ProgramCreator {
    public static void main(String[] args) {
        List<Program> programs = new ArrayList<>();
        programs.add(new BankSystem(new JavaDeveloper()));
        programs.add(new StockExchange(new CppDeveloper()));

        for (Program program : programs) {
            program.developProgram();
        }
    }
}
