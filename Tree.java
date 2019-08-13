package il.co.ilrd.compositetree;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NotDirectoryException;

public class Tree {
	private CompositeFolder root;
	
	public Tree(String path) throws FileNotFoundException, NotDirectoryException {
		if (new File(path).exists() == false) {
			throw new FileNotFoundException();
		} if (new File(path).isFile()) {
			throw new NotDirectoryException("You've entered a file path.");
		}
		root = new CompositeFolder(path);
	}
	
	public void PrintTree() {
		root.Print(0);
	}
	
	private abstract class Component {
		String name;
		abstract void Print(int depth);
	}
	
	private class CompositeFolder extends Component {
		private List<Component> paths;
		
		private CompositeFolder(String path) {
			File file = new File(path);
			this.name = file.getName();
			paths = new ArrayList<Component>();
			for (File f : file.listFiles()) 
			{ 
				if (f.isDirectory()) {
					paths.add(new CompositeFolder(f.getAbsolutePath()));
		        } else {
					paths.add(new CompositeFile(f.getAbsolutePath()));
				}
        	}
		}
		@Override
		void Print(int depth) {
			for (int i = 0; i < depth; ++i) {
				System.out.print(" ");
			}
			System.out.println(name);
			for (Component c : paths) {
				c.Print(depth + 1);
			}
		}
	}
	
	private class CompositeFile extends Component {
		private CompositeFile(String path) {
			File file = new File(path);
			this.name = file.getName();
		}
		@Override
		void Print(int depth) {
			for (int i = 0; i < depth; ++i) {
				System.out.print(" ");
			}
			System.out.println("└── " + name);
		}
	}
	
	public static void main(String[] args) {
		try {
			String path = "/home/ifat/ifat-ori/fs/projects/src/il/co/ilrd";
			Tree tree = new Tree(path);
			tree.PrintTree();
		} catch (FileNotFoundException | NotDirectoryException e) {
			System.out.println("invalid path");
		}
	}
}
