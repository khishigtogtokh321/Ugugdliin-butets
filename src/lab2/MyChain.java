package lab2;


import dataStructures.Chain;

public class MyChain extends Chain {
	public Object[] toArray() {
		Object array[] = new Object[size()];
		for(int i = 0; i< size(); i++) {
			array[i] = this.get(i);
		}
		return array;
	}
	
	public void addRange(Object[] elements) {
	    for (Object element : elements) {        
	        add(size, element);                  
	    }
	}
	
	
	public boolean find(Object element) {
	    for (int i = 0; i < size; i++) {
	        if (get(i).equals(element)) {
	            return true;
	        }
	    }
	    return false;
	}

	
	public MyChain union(MyChain chain) {
	    MyChain list = new MyChain();  
	    
	    for (int i = 0; i< this.size; i++) {
	    	list.add(list.size, this.get(i));
	    }
	    
	    for(int i= 0; i < chain.size; i++) {
	    	Object element = chain.get(i);
	    	if (!list.find(element)) {
	    		list.add(list.size, element);
	    	}
	    }
	    
	   
	    return list;
	}
	
	public MyChain intersection(MyChain chain) {
	    MyChain result = new MyChain();

	    for (int i = 0; i < this.size; i++) {
	        Object element = this.get(i);
	        if (chain.find(element) && !result.find(element)) {
	            result.add(result.size, element);
	        }
	    }

	    return result;
	}
	
	public static void main(String args[]) {
		MyChain myChain = new MyChain();
		myChain.add(0,  8);
		myChain.add(1, 12);
		myChain.add(2, 15);
		
		Object a[];
		
		a = myChain.toArray();
		for(int i = 0; i  < a.length; i++) {
			System.out.println(" " + (int)a[i]);
		}
		
		MyChain list1 = new MyChain();
		list1.addRange(new Object[]{1, 2, 3, 4});

		MyChain list2 = new MyChain();
		list2.addRange(new Object[]{3, 4, 5, 6});

		System.out.println("List1 = " + list1);              
		System.out.println("List2 = " + list2);             
		System.out.println("Union = " + list1.union(list2));  
		System.out.println("Intersection = " + list1.intersection(list2)); 
		
	}
}
