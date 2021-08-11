package trie;

import java.util.ArrayList;

//save checker

/**
 * This class implements a Trie. 
 * 
 * @author Sesh Venugopal
 *
 */
public class Trie 
{
	
	// prevent instantiation
	private Trie() { }
	
	/**
	 * Builds a trie by inserting all words in the input array, one at a time,
	 * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!)
	 * The words in the input array are all lower case.
	 * 
	 * @param allWords Input array of words (lowercase) to be inserted.
	 * @return Root of trie with all words inserted from the input array
	 */
	private static int prefUpto(String insert, String trienode) //helping method
	{
		int x = 0;
		while((x < insert.length() && x < trienode.length()) && trienode.charAt(x) == insert.charAt(x))
		{
			x ++;
		}
		return (x - 1);
		
	}
	
	public static TrieNode buildTrie(String[] allWords) //array
	{
		TrieNode root = new TrieNode(null,null,null);
		
		// when nothing input,
		if (allWords.length == 0)
		{
			return null;			
		}
		
		//Starting case
		
		TrieNode fstChild = new TrieNode(new Indexes(0, (short)(0), (short)(allWords[0].length() - 1))
										,null, null);
		root.firstChild = fstChild;
		TrieNode ptr = null; //trieNode Pointer
		TrieNode prev = null; //previous, because when ptr = null, and sib should go there, need to connect to previous node.
		
		int upto = -1;
		
		
		//proceeding case
		for (int i = 1; i < allWords.length; i++)//
		{
			//re initialize the trie nodes
			ptr = root.firstChild;
			prev = root;
			String currentWord = allWords[i];
			//System.out.println(i);//debugger
			
			
			while (ptr != null) 
			{
				// System.out.print("start  ");//debugger
				int ptrWordIndx = ptr.substr.wordIndex;
				int ptrStartIndx = ptr.substr.startIndex;
				int ptrEndIndx = ptr.substr.endIndex;
				
				
				
				upto = prefUpto(currentWord.substring(ptrStartIndx), allWords[ptrWordIndx].substring(ptrStartIndx,ptrEndIndx + 1));
				// System.out.print(currentWord.substring(ptrStartIndx) + " " + allWords[ptrWordIndx].substring(ptrStartIndx,(ptrEndIndx + 1))); //debugger
				
	
				 if (upto < 0) 
				 {
					 prev = ptr;
					 ptr = ptr.sibling;
					// System.out.println(" -> "); //debugger
				 }
				 
				 else 
				 {
					// System.out.print(" " + upto + " ");//debugger
					// debugger System.out.print(" " + allWords[ptr.substr.wordIndex].substring(ptrStartIndx, ptrEndIndx + 1).length() + " " + currentWord.substring(ptrStartIndx).length() + " ");
					 
					 if (ptrStartIndx + upto == ptrEndIndx) // Case 2.1  startindex + upto = endindex
					 {
						 prev = ptr;
						 ptr = ptr.firstChild;
						 //System.out.println(" down ");//debugger
					 }
					 else if (ptrStartIndx + upto < ptrEndIndx) // Case 2.2  startindex + upto < endindex
					 {
						
						 Indexes newSibIndx = new Indexes(i, (short)(upto + 1 + ptrStartIndx), (short)(currentWord.length() - 1)); 
						 
						 Indexes oriChildIndx = new Indexes(ptr.substr.wordIndex, (short)(upto + 1 + ptrStartIndx), (short) (ptr.substr.endIndex));
						 
						 TrieNode newSib = new TrieNode(newSibIndx,null,null);
						 TrieNode oriChild = new TrieNode(oriChildIndx, null, newSib);
						 
						 ptr.substr.endIndex = (short)(upto + ptr.substr.startIndex); 
						
						 oriChild.firstChild = ptr.firstChild;
						 ptr.firstChild = oriChild;
						 //System.out.println("breaking ");//debugger
						 break;
						 
					 }
				 }
			}
			//case 1.2 ptr.sibling =null 
			if (ptr == null) 
			{
				//System.out.print(" " + prev.substr.startIndex + " ");//debugger
				Indexes newSibIndx1 = new Indexes(i, (short)(prev.substr.startIndex + upto + 1), (short)(currentWord.length() - 1));
				
				TrieNode newSib1 = new TrieNode(newSibIndx1, null, null);
				
				prev.sibling = newSib1;
				//System.out.println(" Sibling ");//debugger
			}

		}
		return root;
	}
	
		
		
		/** COMPLETE THIS METHOD **/
		
		// FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
		// MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
	
	
	
	/**
	 * Given a trie, returns the "completion list" for a prefix, i.e. all the leaf nodes in the 
	 * trie whose words start with this prefix. 
	 * For instance, if the trie had the words "bear", "bull", "stock", and "bell",
	 * the completion list for prefix "b" would be the leaf nodes that hold "bear", "bull", and "bell"; 
	 * for prefix "be", the completion would be the leaf nodes that hold "bear" and "bell", 
	 * and for prefix "bell", completion would be the leaf node that holds "bell". 
	 * (The last example shows that an input prefix can be an entire word.) 
	 * The order of returned leaf nodes DOES NOT MATTER. So, for prefix "be",
	 * the returned list of leaf nodes can be either hold [bear,bell] or [bell,bear].
	 *
	 * @param root Root of Trie that stores all words to search on for completion lists
	 * @param allWords Array of words that have been inserted into the trie
	 * @param prefix Prefix to be completed with words in trie
	 * @return List of all leaf nodes in trie that hold words that start with the prefix, 
	 * 			order of leaf nodes does not matter.
	 *         If there is no word in the tree that has this prefix, null is returned.
	 */
	

	public static ArrayList<TrieNode> completionList(TrieNode root, String[] allWords, String prefix) 
	{
		//System.out.println("check");//debugger
		if (root == null)
		{
			return null;
		}
		
		ArrayList<TrieNode> answerList = new ArrayList<>();
		TrieNode ptr = root.firstChild;
		
		TrieNode fixed = null;
	
		int indx = 0;
		int upto = -1;
		
		while (ptr != null)
		{
			//System.out.println(" start ");//debugger
			int ptrWordIndx = ptr.substr.wordIndex;
			int ptrStartIndx = ptr.substr.startIndex;
			int ptrEndIndx = ptr.substr.endIndex;
			
			upto = prefUpto(allWords[ptrWordIndx].substring(ptrStartIndx,ptrEndIndx + 1), prefix.substring(ptrStartIndx));
			//System.out.println(upto + " " + indx + " ");//debugger
		
			
			if (upto < 0)
			{
				
				if (indx < prefix.length())
				{
				
					ptr = ptr.sibling;
					//System.out.print(" -> ");//debugger
				}
				else
				{
					fixed  = ptr;
					//System.out.println(" fixed1 at: "+ allWords[fixed.substr.wordIndex]);//debugger
					answerList = recursion(fixed, answerList);
					break;
				}
			
			}
			else
			{
				if (prefix.length() - 1 > indx + upto) //still stuffs are left to go
				{
					
					ptr = ptr.firstChild;
					// System.out.print(" down "); //debugger
					indx = indx + upto + 1;
				}
				else 
				{
					if(ptr.firstChild == null) //leaf node
					{
						fixed = ptr;
						// System.out.println(" fixed2 at: "+ allWords[fixed.substr.wordIndex]); //debugger
						answerList.add(ptr);
						break;
					}
					else
					{
						fixed  = ptr.firstChild;
						// System.out.println(" fixed3 at: " + allWords[fixed.substr.wordIndex]); //debugger
						answerList = recursion(fixed, answerList);
						break;
					}
				}
			}
			
		}
		if (ptr == null) //when wrong word input
		{
			return null;
		}
	
		return answerList;
	}
	
	private static ArrayList<TrieNode> recursion(TrieNode node, ArrayList<TrieNode> ans)
	{
		if (node == null)
		{
			return ans;
		}
		else if (node.firstChild == null)
		{
			ans.add(node);
		}
		
		recursion(node.firstChild,ans);
		recursion(node.sibling, ans);
		
		return ans;
		
	}
	
	
	
	
	public static void print(TrieNode root, String[] allWords) 
	{
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}
	
	private static void print(TrieNode root, int indent, String[] words) 
	{
		if (root == null) 
		{
			return;
		}
		for (int i=0; i < indent-1; i++) 
		{
			System.out.print("    ");
		}
		
		if (root.substr != null) 
		{
			String pre = words[root.substr.wordIndex]
							.substring(0, root.substr.endIndex+1);
			System.out.println("      " + pre);
		}
		
		for (int i=0; i < indent-1; i++) 
		{
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) 
		{
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) 
		{
			for (int i=0; i < indent-1; i++) 
			{
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
 }
