import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        //System.out.println("Hello world!");
        boolean loggedOut = true;
        boolean employee = false;

        String currentUser = "";

        UserDao userDao = UserDaoFactory.getUserDao();
        TransactionDao transDao = TransactionDaoFactory.getTransDao();
        AccountDao accountDao = AccountDaoFactory.getAccountDao();
        Scanner scanner = new Scanner(System.in);

        while (loggedOut) {
            // logged out
            System.out.println("Please choose one of the options below: ");
            System.out.println("1 - Log into existing account");
            System.out.println("2 - Register for new account");
            int input = scanner.nextInt();
            switch (input) {
                case 1: {   // login
                    System.out.print("Enter username: ");
                    String username = scanner.next();
                    System.out.print("Enter password: ");
                    String password = scanner.next();
                    User user = userDao.getUserByUsername(username);
                    if (password.equals(user.getPassword())) {
                        if ("employee".equals(user.getRole())) {
                            currentUser = user.getUsername();
                            employee = true;
                            loggedOut = false;
                        } else {
                            currentUser = user.getUsername();
                            employee = false;
                            loggedOut = false;
                        }
                    } else {
                        System.out.println("Incorrect username or password...");
                    }
                    break;
                }
                case 2: {   // register
                    System.out.print("Enter new username: ");
                    String username = scanner.next();
                    System.out.print("Enter new password: ");
                    String password = scanner.next();
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setRole("customer");
                    userDao.addUser(user);
                    break;
                }
                default: {   // wrong number
                    System.out.println("Choose between 1 and 2");
                    break;
                }
            }
        }

        while (!loggedOut && !employee) {
            // logged in as customer
            System.out.println("Welcome, " + currentUser);
            System.out.println("Please choose one of the options below: ");
            System.out.println("1 - Apply for new account");
            System.out.println("2 - View all accounts");
            System.out.println("3 - Make a deposit");
            System.out.println("4 - Make a withdrawal");
            System.out.println("5 - Transfer money between accounts");
            System.out.println("6 - Logout");
            int input = scanner.nextInt();
            switch (input) {
                case 1: {
                    System.out.print("Enter name for new account: ");
                    String name = scanner.next();
                    System.out.print("Enter starting balance for new account: ");
                    int balance = scanner.nextInt();
                    if (balance >= 0) {
                        Account account = new Account();
                        account.setOwner(currentUser);
                        account.setName(name);
                        account.setBalance(balance);
                        account.setStatus("pending");
                        accountDao.addAccount(account);
                    } else {
                        System.out.println("Balance must be more than or equal to 0.");
                    }
                    break;
                }
                case 2: {
                    List<Account> accounts = accountDao.getAccountsByOwner(currentUser);
                    for(Account account: accounts){
                        System.out.println(account);
                    }
                    break;
                }
                case 3: {
                    System.out.print("Enter account id: ");
                    int accId = scanner.nextInt();
                    Account existingAcc = accountDao.getAccountById(accId);
                    System.out.print("Enter the amount you wish to deposit: ");
                    int deposit = scanner.nextInt();
                    if (deposit >= 0) {
                        Account account = new Account();
                        account.setId(accId);
                        account.setOwner(currentUser);
                        account.setName(existingAcc.getName());
                        account.setBalance(existingAcc.getBalance()+deposit);
                        account.setStatus(existingAcc.getStatus());
                        accountDao.updateAccount(account);

                        Transaction trans = new Transaction();
                        trans.setType("deposit");
                        trans.setMadeBy(currentUser);
                        trans.setAmount(deposit);
                        trans.setFromAcc("pockets");
                        trans.setToAcc(existingAcc.getName());
                        transDao.addTransaction(trans);
                    } else {
                        System.out.println("Deposit amount must be more than or equal to 0.");
                    }
                    break;
                }
                case 4: {
                    System.out.print("Enter account id: ");
                    int accId = scanner.nextInt();
                    Account existingAcc = accountDao.getAccountById(accId);
                    System.out.print("Enter the amount you wish to withdrawal: ");
                    int withdrawal = scanner.nextInt();
                    if (withdrawal >= 0) {
                        if (existingAcc.getBalance()-withdrawal >=0) {
                            Account account = new Account();
                            account.setId(accId);
                            account.setOwner(currentUser);
                            account.setName(existingAcc.getName());
                            account.setBalance(existingAcc.getBalance()-withdrawal);
                            account.setStatus(existingAcc.getStatus());
                            accountDao.updateAccount(account);

                            Transaction trans = new Transaction();
                            trans.setType("withdrawal");
                            trans.setMadeBy(currentUser);
                            trans.setAmount(withdrawal);
                            trans.setFromAcc(existingAcc.getName());
                            trans.setToAcc("pockets");
                            transDao.addTransaction(trans);
                        } else {
                            System.out.println("Withdrawal amount surpasses what is in account.");
                        }
                    } else {
                        System.out.println("Withdrawal amount must be more than or equal to 0.");
                    }
                    break;
                }
                case 5: {
                    System.out.print("Enter id of account you want to transfer from: ");
                    int fromAccId = scanner.nextInt();
                    Account fromAcc = accountDao.getAccountById(fromAccId);
                    System.out.print("Enter id of account you want to transfer to: ");
                    int toAccId = scanner.nextInt();
                    Account toAcc = accountDao.getAccountById(toAccId);
                    System.out.print("Enter the amount you wish to transfer: ");
                    int transfer = scanner.nextInt();
                    if (transfer >= 0) {
                        if (fromAcc.getBalance()-transfer >= 0) {
                            Account account = new Account();
                            account.setId(fromAccId);
                            account.setOwner(currentUser);
                            account.setName(fromAcc.getName());
                            account.setBalance(fromAcc.getBalance()-transfer);
                            account.setStatus(fromAcc.getStatus());
                            accountDao.updateAccount(account);

                            Account account2 = new Account();
                            account2.setId(toAccId);
                            account2.setOwner(currentUser);
                            account2.setName(toAcc.getName());
                            account2.setBalance(toAcc.getBalance()+transfer);
                            account2.setStatus(toAcc.getStatus());
                            accountDao.updateAccount(account2);

                            Transaction trans = new Transaction();
                            trans.setType("transfer");
                            trans.setMadeBy(currentUser);
                            trans.setAmount(transfer);
                            trans.setFromAcc(fromAcc.getName());
                            trans.setToAcc(toAcc.getName());
                            transDao.addTransaction(trans);
                        } else {
                            System.out.println("Transfer amount surpasses what is in from account.");
                        }
                    } else {
                        System.out.println("Transfer amount must be more than or equal to 0.");
                    }
                    break;
                }
                case 6: {
                    currentUser = "";
                    employee = false;
                    loggedOut = true;
                    break;
                }
                default: {
                    System.out.println("Choose between 1 - 6");
                    break;
                }
            }
        }

        while (!loggedOut && employee) {
            // logged in as employee
            System.out.println("Welcome, " + currentUser);
            System.out.println("Please choose one of the options below: ");
            System.out.println("1 - View all pending accounts");
            System.out.println("2 - Manage pending accounts");
            System.out.println("3 - View customer's accounts");
            System.out.println("4 - View log of all transactions");
            System.out.println("5 - Logout");
            int input = scanner.nextInt();
            switch (input) {
                case 1: {
                    List<Account> accounts = accountDao.getPendingAccounts();
                    for(Account account: accounts){
                        System.out.println(account);
                    }
                    break;
                }
                case 2: {
                    System.out.print("Enter id of pending account: ");
                    int accId = scanner.nextInt();
                    Account existingAcc = accountDao.getAccountById(accId);
                    System.out.print("Enter new status of account: ");
                    String stat = scanner.next();
                    Account account = new Account();
                    account.setId(accId);
                    account.setOwner(existingAcc.getOwner());
                    account.setName(existingAcc.getName());
                    account.setBalance(existingAcc.getBalance());
                    account.setStatus(stat);
                    accountDao.updateAccount(account);
                    break;
                }
                case 3: {
                    System.out.print("Enter username of customer: ");
                    String customer = scanner.next();
                    List<Account> accounts = accountDao.getAccountsByOwner(customer);
                    for(Account account: accounts){
                        System.out.println(account);
                    }
                    break;
                }
                case 4: {
                    List<Transaction> transactions = transDao.getTransactions();
                    for(Transaction trans: transactions){
                        System.out.println(trans);
                    }
                    break;
                }
                case 5: {
                    currentUser = "";
                    employee = false;
                    loggedOut = true;
                    break;
                }
                default: {
                    System.out.println("Choose between 1 - 5");
                    break;
                }
            }
        }
    }
}