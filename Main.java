import java.util.Scanner;

import Classes.Bank;
import Classes.Branch;
import Structures.Stack;
import Classes.Neighbor.NeighborNode;
import Classes.Neighbor.Neighborhood;
import Structures.Tree.KdTree.KdTree;
import Structures.Tree.KdTree.KdNode;
import Structures.Tree.TrieTree.TrieTree;


public class Main {
	public static Stack commands;
	public static TrieTree<Bank> bankTrieTree;
	public static TrieTree<Branch> branchTrieTree;
	public static Neighborhood neighborhoods;
	public static KdTree<Bank> banksKdTree;
	public static KdTree<Branch> branchesKdTree;
	public static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		printWelcome();
		showHelp();
		initialize();
		while (true) {
			getCommand();
			System.out.println(commands);
		}
	}

	public static void initialize() {
		commands = new Stack();
		neighborhoods = new Neighborhood();
		bankTrieTree = new TrieTree<Bank>();
		branchTrieTree = new TrieTree<Branch>();
		banksKdTree = new KdTree<Bank>();
		branchesKdTree = new KdTree<Branch>();
	}

	public static void addNeighbor() {
		System.out.println("\n\n");
		System.out.println("**Please enter the coordinate of neighborhood: ");

		int[] x = new int[2];
		System.out.print("> X0: ");
		x[0] = getNumber("X0");
		System.out.print("> X1: ");
		x[1] = getNumber("X1");

		while (x[0] == x[1]) {
			System.out.println("** X0 and X1 cannot be same **");
			System.out.print("> X1: ");
			x[1] = getNumber("X1");
		}

		int[] y = new int[2];
		System.out.print("> Y0: ");
		y[0] = getNumber("Y0");
		System.out.print("> Y1: ");
		y[0] = getNumber("Y1");

		while (y[0] == y[1]) {
			System.out.println("** Y0 and Y1 cannot be same **");
			System.out.print("> Y1: ");
			x[1] = getNumber("Y1");
		}
		System.out.print("> name: ");
		String name = getString("name");

		if (neighborhoods.search(name) != null ) {
			System.out.println("** There is already a neighbor exist with name " + name);
			return;
		}

		if (neighborhoods.search(x, y) != null) {
			System.out.println("There is already a neighborhood exist with These coordinate.");
			return;
		}

		NeighborNode n = new NeighborNode(x, y, name);
		neighborhoods.insert(n);
		commands.push("addN x0=" + x[0] + " x1=" + x[1] + " y0=" + y[0] + " y1=" + y[1] + " name=" + name );
		return;
	}

	public static void addBank() {
		System.out.println("** Please enter information required: ");
		int[] coordinate = new int[2];
		System.out.print("> X: ");
		coordinate[0] = getNumber("X");
		System.out.print("> Y: ");
		coordinate[1] = getNumber("Y");
		
		if (banksKdTree.search(coordinate) != null) {
			System.out.println("** There is a << bank >> already exist with this coordinates");
			return;
		}

		if (branchesKdTree.search(coordinate) != null) {
			System.out.println("** There is a << branch >> already exist with this coordinates");
			return;
		}

		System.out.print("> name: ");
		String name = getString("name");

		if (bankTrieTree.search(name) != null) {
			System.out.println("** There is already a bank exists with name " + name);
			return;
		}

		NeighborNode[] neighborNodes = neighborhoods.searchArea(coordinate[0], coordinate[1]);
		if (neighborNodes.length == 0 || neighborNodes[0] == null) {
			System.out.println("*** There is no neighborhood in coordinate that you entered");
			return;
		}
		Bank b = new Bank(coordinate, name);
		for (NeighborNode x : neighborNodes) {
			if (x != null) {
				x.banks.addPoint(coordinate, b);
			}
		}
		bankTrieTree.insert(name, b);
		banksKdTree.addPoint(coordinate, b);
		commands.push("addB x=" + coordinate[0] + " y=" + coordinate[1] + " name=" + name);
		return;
	}

	public static void addBranch() {
		System.out.println("** Please enter information required: ");
		int[] coordinate = new int[2];
		System.out.print("> X: ");
		coordinate[0] = getNumber("X");
		System.out.print("> Y: ");
		coordinate[1] = getNumber("Y");
		if (banksKdTree.search(coordinate) != null) {
			System.out.println("** There is already a <<bank>> exist with this coordinates");
			return;
		}
		
		if (branchesKdTree.search(coordinate) != null) {
			System.out.println("** There is already a <<branch>> exist with this coordinates");
			return;
		}

		System.out.println("> bank name: ");
		String bankName = getString("bank name");
		Bank b = bankTrieTree.search(bankName);
		if (b == null) {
			System.out.println("** There is no bank exist with name " + bankName);
			return;
		}

		System.out.println("> branch name: ");
		String branchName = getString("branch name");
		if (branchTrieTree.search(branchName) != null) {
			System.out.println("** There is already a branch exists with name " + branchName);
		}

		Branch branch = new Branch(coordinate, bankName, branchName);
		branchTrieTree.insert(branchName, branch);
		branchesKdTree.addPoint(coordinate, branch);
		b.addBranch(branch);
		commands.push("addBr x=" + coordinate[0] + " y=" + coordinate[1] + " name=" + branchName);
		return;
	}

	public static void deleteBranch() {
		int[] coordinate = new int[2];
		System.out.println("** Please enter information required: ");
		System.out.print("> X: ");
		coordinate[0] = getNumber("X");
		System.out.print("> Y: ");
		coordinate[1] = getNumber("Y");

		Branch branch = branchesKdTree.search(coordinate).data;
		if (branch == null) {
			System.out.println("** There is no branch with given coordinate");
			return;
		}

		Bank b = bankTrieTree.search(branch.getBankName());
		b.deleteBranch(branch);
		branchesKdTree.delete(coordinate);
		branchTrieTree.delete(branch.getName());
		commands.push("delBr x=" + coordinate[0] + " y=" + coordinate[1] + " name=" + branch.getName() + " bankName=" + branch.getBankName());
	}

	public static void neighborhoodBankList() {
		System.out.println("** Please enter information required: ");
		System.out.print("> neighborhood name: ");
		String name = getString("neighborhood name");

		NeighborNode n = neighborhoods.search(name);
		if (n == null) {
			System.out.println("** no neighbor found with name " + name);
			return;
		}
		n.banks.printAll();
	}

	public static void bankBranchesList() {
		System.out.println("** Please enter information required: ");
		System.out.print("> bank name: ");
		String name = getString("bank name");
		Bank b = bankTrieTree.search(name);
		if (b == null) {
			System.out.println("no bank found with name " + name);
			return;
		}
		@SuppressWarnings("unchecked")
		KdNode<Branch>[] nodes = (KdNode<Branch>[]) b.branches.getAllNodes();
		for (KdNode<Branch> node : nodes) {
			Branch branch =  branchTrieTree.search(node.data.getName());
			System.out.println(branch);
		}
	}

	public static void nearestToMe(String type) {
		int[] coordinate = new int[2];
		System.out.println("\n** Please enter information required");
		System.out.println("> X: ");
		coordinate[0] = getNumber("X");
		System.out.println("> Y: ");
		coordinate[1] = getNumber("Y");
		System.out.print("The nearest bank to point given is: ");
		if (type.equals("bank")) {
			System.out.println(banksKdTree.nearest(coordinate));
		} else {
			KdNode<Bank> nb = banksKdTree.nearest(coordinate);
			Branch branch = branchTrieTree.search(nb.data.getName());
			System.out.println(branch);
		}
	}

	public static void findBankInRange() {
		int[] coordinate = new int[2];
		System.out.println("\n** Please enter information required");
		System.out.println("> X: ");
		coordinate[0] = getNumber("X");
		System.out.println("> Y: ");
		coordinate[1] = getNumber("Y");
		System.out.println("> range: ");
		int range = getNumber("range");

		System.out.print("Available banks are: ");
		@SuppressWarnings("unchecked")
		KdNode<Bank>[] availBanks = (KdNode<Bank>[])banksKdTree.findAllInRange(coordinate, range);
		for (KdNode<Bank> b : availBanks) {
			if (b == null) {
				break;
			} else {
				System.out.println(b);
			}
		}
	}

	public static void bankWithLargestBranch() {
		@SuppressWarnings("unchecked")
		KdNode<Bank>[] allBanks = (KdNode<Bank>[])banksKdTree.getAllNodes();
		Bank mostBank = bankTrieTree.search(allBanks[0].data.getName());
		for (int i = 1; i < allBanks.length; i++) {
			Bank x = bankTrieTree.search(allBanks[i].data.getName());
			if (x != null) {
				if (x.getBranchNumber() > mostBank.getBranchNumber()) {
					mostBank = x;
				}
			}
		}

		System.out.println(mostBank);
	}

	public static void famousBank() {
		@SuppressWarnings("unchecked")
		KdNode<Branch>[] totalBranches = (KdNode<Branch>[]) branchesKdTree.getAllNodes();

		String[] bankNames = new String[totalBranches.length];
		int[] branchVisitedTotal = new int[totalBranches.length];

		for (KdNode<Branch> kdNode : totalBranches) {
			Branch branch = branchTrieTree.search(kdNode.data.getName());
			for (int i = 0; i < totalBranches.length; i++) {
				if (bankNames[i].isEmpty()) {
					bankNames[i] = branch.getBankName();
					branchVisitedTotal[i] += branch.getVisited();
				} else if (bankNames[i].equals(branch.getBankName())) {
					branchVisitedTotal[i] += branch.getVisited();
				}
			}
		}

		int index = 0;
		for (int i = 1; i < totalBranches.length; i++) {
			if (branchVisitedTotal[i] > branchVisitedTotal[index]) {
				index = i;
			}
		}

		System.out.println(bankTrieTree.search(bankNames[index]));
	}

	public static void undo(int until) {
		String done = "";
		for (int i = 0; i < until; i++) {
			done = commands.pop();
			String command = done.substring(0, done.indexOf(" ") + 1);

			if (command.equals("addN")) {
				int[] x = {
					Integer.parseInt(done.substring(done.indexOf("x0=") + 3, done.indexOf(" x1="))),
					Integer.parseInt(done.substring(done.indexOf("x1=") + 3, done.indexOf(" y0=")))
				};
				int[] y = {
					Integer.parseInt(done.substring(done.indexOf("y0=") + 3, done.indexOf(" y1="))),
					Integer.parseInt(done.substring(done.indexOf("y1=") + 3, done.indexOf(" name=")))
				};
				String name = done.substring(done.indexOf("name=") + 5);
				neighborhoods.delete(x, y);
			}
			else if (command.equals("addB")) {
				int[] coordinate = {
					Integer.parseInt(done.substring(done.indexOf("x=") + 2, done.indexOf(" y"))),
					Integer.parseInt(done.substring(done.indexOf("y=") + 2, done.indexOf(" name=")))
				};
				String name = done.substring(done.indexOf("name=") + 5);
				bankTrieTree.delete(name);
				banksKdTree.delete(coordinate);
			}
			else if (command.equals("addBr")) {
				int[] coordinate = {
					Integer.parseInt(done.substring(done.indexOf("x=") + 2, done.indexOf(" y"))),
					Integer.parseInt(done.substring(done.indexOf("y=") + 2, done.indexOf(" name=")))
				};
				String name = done.substring(done.indexOf("name=") + 5);
				Branch bb = branchTrieTree.search(name);
				branchTrieTree.delete(name);
				branchesKdTree.delete(coordinate);
				(bankTrieTree.search(bb.getBankName())).deleteBranch(bb);
			}
			else if (command.equals("delBr")) {
				int[] coordinate = {
					Integer.parseInt(done.substring(done.indexOf("x=") + 2, done.indexOf(" y"))),
					Integer.parseInt(done.substring(done.indexOf("y=") + 2, done.indexOf(" name=")))
				};
				String branchName = done.substring(done.indexOf("name=") + 5, done.indexOf(" bankName="));
				String bankName = done.substring(done.indexOf("bankName=") + 9);
				Branch bb = new Branch(coordinate, branchName, bankName);
				Bank b = bankTrieTree.search(bankName);
				b.addBranch(bb);
				branchesKdTree.addPoint(coordinate, bb);
				branchTrieTree.insert(branchName, bb);
			}
		}
	}

	public static void getCommand() {
		System.out.println("\nPlease enter your command: ");
		System.out.print("> ");
		String command = in.nextLine();
		if (command.equalsIgnoreCase("q") || command.equalsIgnoreCase("quit")) {
			System.out.println("\n bye bye \n");
			System.exit(0);
		}

		if (command.equals("addN")) {
			addNeighbor();
		}
		else if (command.equals("addB")) {
			addBank();
		}
		else if (command.equals("addBr")) {
			addBranch();
		}
		else if (command.equals("delBr")) {
			deleteBranch();
		}
		else if (command.equals("listB")) {
			neighborhoodBankList();
		}
		else if (command.equals("listBrs")) {
			bankBranchesList();
		}
		else if (command.equals("nearB")) {
			nearestToMe("bank");
		}
		else if (command.equals("nearBr")) {
			nearestToMe("branch");
		}
		else if (command.equals("availB")) {
			findBankInRange();
		}
		else if (command.equals("mostBr")) {
			bankWithLargestBranch();
		}
		else if (command.equals("fameB")) {
			famousBank();
		}
		else if (command.substring(0, command.indexOf(" ")).equals("undo")) {
			String number = command.substring(command.indexOf(" ") + 1);
			if (number.matches(".*[^0-9].*")) {
				System.out.println("*** you need to enter a number after undo");
			} else {
				undo(Integer.parseInt(number));
			}
		}
		else if (command.equalsIgnoreCase("clear")) {
			System.out.print("\033[H\033[2J"); // this line will clear console.
			System.out.flush();
		}
		else if (command.equals("h") || command.equals("help") || command.equals("?")) {
			showHelp();
		}
		else {
			System.out.println("\n***Sorry your command not found please try again");
		}
	}

	public static int getNumber(String message) {
		String input = in.nextLine();
		try {
			return Integer.parseInt(input);
		} catch (NumberFormatException e) {
			System.out.println("** You need to enter a number **");
			System.out.print("> " + message + ": ");
			return getNumber(message);
		}
	}

	public static String getString(String message) {
		String x = in.nextLine();
		if(x.matches(".*[^A-Za-z0-9].*")) {
			System.out.println("** Your string must be in [A-Z], [a-z], [0-9] **");
			System.out.print("> " + message + ": ");
			return getString(message);
		} else {
			return x;
		}
	}

	public static void showHelp() {
		System.out.println("\n\n");
		System.out.println("\t-Here is commands that you can enter: \n");
		System.out.println("> addN => this command will add new neighborhood");
		System.out.println("> addB => this command will add new main bank");
		System.out.println("> addBr => this command will add new branch to main bank");
		System.out.println("> delBr => this command will remove a branch");
		System.out.println("> listB => this command will show all banks in a neighborhood");
		System.out.println("> listBrs => this command will show all coordinate of bank branches");
		System.out.println("> nearB => this command will show nearest bank to you");
		System.out.println("> nearBr => this command will show nearest bank branch to you");
		System.out.println("> availB => this command will show all available banks in radius of R from you");
		System.out.println("> mostBr => this command will show the bank that have most branch of all");
		System.out.println("> fameB => this command will show the most visited bank of all");
		System.out.println();
		System.out.println("(*) undo P => you can use this command to undo the commands to you entered");
		System.out.println("(*) h or help or ? => show help");
		System.out.println("(*) clear => clear screen");
		System.out.println("(*) q or quit => exits the program");
	}

	public static void printWelcome() {
		System.out.print("\033[H\033[2J"); // this line will clear console.
		System.out.flush();
		System.out.println("\t\tM#########M MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM  MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM ");
		System.out.println("\t\tM#\"\"\"\"\"\"\"'M MMP\"\"\"\"\"\"\"MMM\"\"\"\"\"\"\"`YMM\"\"MMMMM\"\"M  MM\"\"\"\"\"\"\"\"`MM\"\"MM\"\"\"\"\"\"\"`YMM\"\"\"\"\"\"'YMMMM\"\"\"\"\"\"\"\"`MMM\"\"\"\"\"\"\"`MM ");
		System.out.println("\t\t##  mmmm. `MM' .mmmm  MMM  mmmm.  MM  MMMM' .M  MM  mmmmmmmMM  MM  mmmm.  MM  mmmm. `MMM  mmmmmmmMMM  mmmm,  M ");
		System.out.println("\t\t#'        .MM         `MM  MMMMM  MM       .MM  M'      MMMMM  MM  MMMMM  MM  MMMMM  MM`      MMMMM'        .M ");
		System.out.println("\t\tM#  MMMb.'YMM  MMMMM  MMM  MMMMM  MM  MMMb. YM  MM  MMMMMMMMM  MM  MMMMM  MM  MMMMM  MMM  MMMMMMMMMM  MMMb. \"M ");
		System.out.println("\t\tM#  MMMM'  MM  MMMMM  MMM  MMMMM  MM  MMMMb  M  MM  MMMMMMMMM  MM  MMMMM  MM  MMMM' .MMM  MMMMMMMMMM  MMMMM  M ");
		System.out.println("\t\tM#       .;MM  MMMMM  MMM  MMMMM  MM  MMMMM  M  MM  MMMMMMMMM  MM  MMMMM  MM       .MMMM        .MMM  MMMMM  M ");
		System.out.println("\t\tM#########M MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM  MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM ");
	}
}