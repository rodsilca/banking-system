package application;

import model.entities.Account;
import model.entities.CheckingAccount;
import model.entities.Client;
import model.entities.SavingsAccount;
import model.enums.ClientGender;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

// ve se tem como fazer tipo um login, (todas as acoes seram de uma conta especifica sem ter a necessidade de indentificar o numero da conta)
// usar o jdbc quando o programa estiver pronto

public class Program {
    public static List<Account> accounts = new ArrayList<>();

    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
//        accounts.add(new CheckingAccount("1010", String.valueOf(accounts.size()+1),100.0,new Client("reos","323232332",ClientGender.MASCULINE,LocalDate.parse("2020-02-21")),1000.0));
//        accounts.add(new SavingsAccount("2020", String.valueOf(accounts.size()+1),100.0,new Client("fdsfsdf","323232332",ClientGender.MASCULINE,LocalDate.parse("2020-02-21"))));
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
        System.out.println("\n--- WELCOME ---");
        System.out.println("1. Create account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. List all accounts");
        System.out.println("6. Transaction logs");
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
                break;

            case 2:
                account = new SavingsAccount("2020", String.valueOf(accounts.size()+1),0.0,client);
                accounts.add(account);

                System.out.println("Success! Account created!");
                System.out.println("Agency: " + account.getAgency() + ", Conta: " + account.getAccountNumber());
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
        System.out.print("Digite o número da conta para depósito: ");
        String accountNumber = sc.nextLine();

        Account conta = searchForAccount(accountNumber);

        if (conta != null) {
            System.out.println(conta.getAgency());
            System.out.println(conta.getClient().getName());
            System.out.print("Digite o valor a ser depositado: ");
            double value = sc.nextDouble();

            conta.deposit(value);

            System.out.println("Depósito realizado com sucesso!");
            System.out.println("Novo saldo: R$ " + conta.getBalance());
        } else {
            System.out.println("Erro: Conta não encontrada.");
        }
    }

    private static void withdraw(){
        System.out.println("\n--- Saque em Conta ---");
        System.out.print("Digite o número da conta para saque: ");
        String accountNumber = sc.nextLine();

        Account conta = searchForAccount(accountNumber);

        if (conta != null) {
            System.out.println(conta.getAgency());
            System.out.println(conta.getClient().getName());
            System.out.print("Digite o valor a ser sacado: ");
            double value = sc.nextDouble();

            conta.withdraw(value);

            System.out.println("Depósito realizado com sucesso!");
            System.out.println("Novo saldo: R$ " + conta.getBalance());
        } else {
            System.out.println("Erro: Conta não encontrada.");
        }
    }

    private static void transferToAccount(){
        System.out.println("\n--- Tranferencia entre Contas ---");
        System.out.print("Digite o número da conta que o valor sera retirado: ");
        String accountNumber = sc.nextLine();

        Account contaRetirada = searchForAccount(accountNumber);

        System.out.print("Digite o número da conta que o valor sera destinado: ");
        accountNumber = sc.nextLine();
        Account contaDestino = searchForAccount(accountNumber);

        if (contaRetirada != null && contaDestino !=null) {
            System.out.print("Digite o valor a ser transferido: ");
            double value = sc.nextDouble();

            contaRetirada.transfer(value,contaDestino);

            System.out.println("Transferencia realizada com sucesso!");
            System.out.println("Novo saldo da conta de retirada: R$ " + contaRetirada.getBalance());
            System.out.println("Novo saldo da conta de destino: R$ " + contaDestino.getBalance());

        } else if (contaRetirada == null) {
            System.out.println("Erro: Conta para retirada não encontrada.");
        }else{
            System.out.println("Erro: Conta de destino para o valor não encontrada.");
        }
    }

    private static void transactionLogs(){
        System.out.println("\n--- Transacoes ---");
        System.out.print("Digite o número da conta: ");
        String accountNumber = sc.nextLine();

        Account account = searchForAccount(accountNumber);

        System.out.println("\n---- TRANSACOES ---");

        account.getTransactionList();

    }

    private static void listAccounts() {
        System.out.println("\n--- Lista de Contas Cadastradas ---");
        if (accounts.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada no momento.");
        } else {
            accounts.forEach(System.out::println);
        }
    }
}
