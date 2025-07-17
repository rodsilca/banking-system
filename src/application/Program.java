package application;

import model.entities.Account;
import model.entities.CheckingAccount;
import model.entities.Client;
import model.entities.SavingsAccount;
import model.enums.ClientGender;

import java.time.LocalDate;
import java.util.*;

// usar o jdbc quando o programa estiver pronto

public class Program {
    public static List<Account> accounts = new ArrayList<>();

    public static Account loggedInAccount;

    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        accounts.add(new CheckingAccount("1010", String.valueOf(accounts.size()+1),100.0,new Client("reos","323232332",ClientGender.MASCULINE,LocalDate.parse("2020-02-21")),1000.0));
        accounts.add(new SavingsAccount("2020", String.valueOf(accounts.size()+1),100.0,new Client("fdsfsdf","323232332",ClientGender.MASCULINE,LocalDate.parse("2020-02-21"))));

        loggedInAccount = accounts.getFirst();
        while (true){
            showMenu();
            System.out.print("Escolha uma opção: ");
            int opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    depositAccount();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    transferToAccount();
                    break;
                case 5:
                    listAccounts();
                    break;
                case 6:
                    transactionLogs();
                    break;
                case 7:
                    switchAccounts();
                    break;
                case 0:
                    System.out.println("Bye!! See you soon");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid Input. Try again.");
            }
        }
    }

    private static void showMenu(){
        System.out.println("\nCurrent Account:");
        if (loggedInAccount != null){
            System.out.println("Account Number: " + loggedInAccount.getAccountNumber());
            System.out.println("Holder: "+ loggedInAccount.getClient().getName().toUpperCase());
            System.out.println("Balance: R$ "+ String.format("%.2f",loggedInAccount.getBalance()));
        }else {
            System.out.println("Vc nao esta logado em nenhuma conta.");
        }

        System.out.println("\n--- WELCOME ---");
        System.out.println("1. Create account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. List all accounts");
        System.out.println("6. Transaction logs");
        System.out.println("7. Switch accounts");
        System.out.println("0. Leave");
        System.out.println("---------------------------------");
    }

    private static void createAccount() {
        System.out.println("\n--- Criação de Nova Conta ---");

        System.out.println("Type of account:");
        System.out.println("1 - Checking account");
        System.out.println("2 - Savings account");
        int option = sc.nextInt();

        System.out.println("Name:");
        String name = sc.next();

        System.out.println("CPF:");
        String cpf = sc.next();

        System.out.println("Gender: 1-M 2-F");
        int genderOption = sc.nextInt();
        sc.nextLine();

        ClientGender gender = null;
        switch (genderOption) {
            case 1:
                gender = ClientGender.MASCULINE;
                break;
            case 2:
                gender = ClientGender.FEMININE;
                break;
            default:
                System.out.println("Invalid Input, try again.");
        }

        System.out.print("Date of birth(YYYY-MM-DD): ");
        LocalDate dateBirth = LocalDate.parse(sc.nextLine());

        Client client = new Client(name, cpf, gender, dateBirth);

        Account account;

        switch (option) {
            case 1:
                account = new CheckingAccount("1010", String.valueOf(accounts.size()+1),0.0,client,1000.0);
                accounts.add(account);

                System.out.println("Success! Account created!");
                System.out.println("Agency: " + account.getAgency() + ", Conta: " + account.getAccountNumber());

                loggedInAccount = account;

                break;

            case 2:
                account = new SavingsAccount("2020", String.valueOf(accounts.size()+1),0.0,client);
                accounts.add(account);

                System.out.println("Success! Account created!");
                System.out.println("Agency: " + account.getAgency() + ", Conta: " + account.getAccountNumber());

                loggedInAccount = account;

                break;
        }
    }

    private static Account searchForAccount(String numero) {
        // Usando a Stream API para encontrar a conta de forma eficiente
        Optional<Account> foundAccount = accounts.stream()
                .filter(account -> account.getAccountNumber().equals(numero))
                .findFirst();

        return foundAccount.orElse(null); // Retorna a conta se encontrou, ou null se não encontrou.
    }

    private static void depositAccount() {

        System.out.println("\n--- Depósito em Conta ---");

        if (loggedInAccount != null) {
            System.out.println(loggedInAccount.getAgency());
            System.out.println(loggedInAccount.getClient().getName());
            System.out.print("Digite o valor a ser depositado: ");
            double value = sc.nextDouble();

            loggedInAccount.deposit(value);

            System.out.println("Depósito realizado com sucesso!");
            System.out.println("Novo saldo: R$ " + String.format("%.2f",loggedInAccount.getBalance()));
        } else {
            System.out.println("Erro: Conta não encontrada.");
        }
    }

    private static void withdraw(){
        System.out.println("\n--- Saque em Conta ---");

        if (loggedInAccount  != null) {
            System.out.println(loggedInAccount.getAgency());
            System.out.println(loggedInAccount.getClient().getName());
            System.out.print("Digite o valor a ser sacado: ");
            double value = sc.nextDouble();

            loggedInAccount.withdraw(value);

            System.out.println("Depósito realizado com sucesso!");
            System.out.println("Novo saldo: R$ " + String.format("%.2f",loggedInAccount.getBalance()));
        } else {
            System.out.println("Erro: Conta não encontrada.");
        }
    }

    private static void transferToAccount(){
        System.out.println("\n--- Tranferencia entre Contas ---");

        System.out.print("Digite o número da conta que o valor sera destinado: ");
        String accountNumber = sc.nextLine();
        Account contaDestino = searchForAccount(accountNumber);

        if (loggedInAccount != null && contaDestino !=null) {
            System.out.print("Digite o valor a ser transferido: ");
            double value = sc.nextDouble();

            loggedInAccount.transfer(value,contaDestino);

            System.out.println("Transferencia realizada com sucesso!");
            System.out.println("Novo saldo da conta de retirada: R$ " + String.format("%.2f",loggedInAccount.getBalance()));
            System.out.println("Novo saldo da conta de destino: R$ " + contaDestino.getBalance());

        } else if (loggedInAccount == null) {
            System.out.println("Erro: Conta para retirada não encontrada.");
        }else{
            System.out.println("Erro: Conta de destino para o valor não encontrada.");
        }
    }

    private static void transactionLogs(){
        System.out.println("\n--- Transacoes ---");
//        System.out.print("Digite o número da conta: ");
//        String accountNumber = sc.nextLine();
//
//        Account account = searchForAccount(accountNumber);

        System.out.println("\n---- TRANSACOES ---");

        if (loggedInAccount !=null){
            loggedInAccount.getTransactionList();
        }else {
            System.out.println("You need to log in to do this operation.");
        }


    }

    private static void listAccounts() {
        System.out.println("\n--- Lista de Contas Cadastradas ---");
        if (accounts.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada no momento.");
        } else {
            accounts.forEach(System.out::println);
        }
    }

    private static void switchAccounts(){
        System.out.println("\n--- SWITCHING ACCOUNTS ---");
        System.out.println("Type the number of the account you wish to switch to: ");
        String accountNumber = sc.nextLine();

        Account accountToLogIn = searchForAccount(accountNumber);


        if (accountToLogIn!=null && Objects.equals(accountToLogIn.getAccountNumber(), loggedInAccount.getAccountNumber())){
            System.out.println("Already logged into this account");

        } else if (accountToLogIn !=null){

            System.out.println("Login Successful !");
            loggedInAccount = accountToLogIn;

        } else {
            System.out.println("\nAccount not found.");
        }

    }
}
