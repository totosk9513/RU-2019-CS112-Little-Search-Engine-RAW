package friends;

import java.util.ArrayList;
import java.util.HashMap;

import structures.Queue;
import structures.Stack;

//Minseok Park
public class Friends 
{

	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null or empty array list if there is no
	 *         path from p1 to p2
	 */

	
	private static int[] edgeToArrMakerBFS(Graph g, String p1)
	{
		
		HashMap<String, Integer> dbMap = g.map;
		Person[] dbPpl = g.members;
		int dbSize = dbMap.size();
		
		Queue<Person> q = new Queue<Person>();
		boolean[] isMarked = new boolean[dbSize];
		//System.out.println(isMarked.length);
		int[] edgeTo = new int[dbSize];
		//System.out.println(edgeTo.length);
		for (int i = 0 ; i < edgeTo.length; i ++) 
		{
			edgeTo[i] = -1;
		}
		
		Person origin = dbPpl[dbMap.get(p1)];
		int index = dbMap.get(origin.name);
		
		//table setter
		isMarked[index] = true;
		q.enqueue(origin);
		
		
		while (q.isEmpty() != true)
		{
			//System.out.println("\n\nloop start" );
			Person psn = q.dequeue(); //deq
			//System.out.println(psn.name);
			
			int dQPsnAdd = dbMap.get(psn.name);
			
			if (isMarked[dQPsnAdd] == false) //throw an exception. This BFS algorithm checks a person's isMarked before enq the person. 
												//but if dequeued person is not marked as visited, then there is some problem. 
			{
				System.out.println("Exception: dequeued person data from the Q is not marked. "
									+ "whenever enquing a person data, it should be marked");
				//result.clear();
				//return result;
				return null;
			}
			else
			{
				Friend temp = psn.first; 
				while (temp != null)//start traversing dequeued person's friend list (linked list)
				{
					int frndsIdx = temp.fnum;  
					Person frnd = dbPpl[frndsIdx];
					if (isMarked[frndsIdx] == false)
					{
						q.enqueue(frnd);
						//System.out.print(frnd.name + " ");
						isMarked[frndsIdx] = true;
						edgeTo[frndsIdx] = dQPsnAdd;
					}
					temp = temp.next;
				}
			}
		}
		return edgeTo;
		
	}
	
	
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) 
	{
		//BSF로 이용해서 p1부터 p2까지의 루트를 찾고 (큐 써야함), edgeto를 스택으로 반대로 넣어서 다시 빼면 shortest path가 나옴 
		
		ArrayList<String> result = new ArrayList<String>();
		
		if (g == null)
		{
			System.out.println("Exception: the graph is null. check its building impletation or its data");
			result.clear();
			return result;
		}
		else if (p1 == null || p2 == null)
		{
			System.out.println("Exception: the input string(s) is(are) not valid");
			result.clear(); 
			return result;
		}
		
		Person origin = null;
		Person ending = null;
		
		HashMap<String, Integer> dbMap = g.map;
		Person[] dbPpl = g.members;
		
		//int dbSize = dbMap.size();
		int originAddress = dbMap.get(p1);
		int endingAddress = dbMap.get(p2);
		//System.out.println(dbSize + " " + originAddress + " " + endingAddress);
		
		origin = dbPpl[originAddress];
		ending = dbPpl[endingAddress];
		//System.out.println(origin.name + " " + ending.name);
		
		if (origin == null || ending == null)
		{
			System.out.println("Exception: Either p1 or p2 (or both) are not found. Both p1 and p2 need to be found in the graph to start." );
			//throw an exception 
			result.clear();
			return result;
			//return null;
		}
		
		Stack<Person> stk = new Stack<Person>(); // I think i can just use integer for the Q and Stack.... cuz int is address 
		
		
		
		 
		//System.out.println(edgeTo.length);
		
		int[] edgeTo = null; // should i just set its size as dbSize?
		
		if (edgeToArrMakerBFS(g, p1) == null)
		{
			System.out.println("Exception catched: nullify the result");
			result.clear();
			return result;
		}
		else
		{
			edgeTo = edgeToArrMakerBFS(g, p1);
		}
		
		if (edgeTo == null)
		{
			System.out.println("Bug reported: the edgeTo[] is null, cannot start the stack algorithm");
			result.clear();
			return result;
		}
		//stk table setter
		stk.push(ending);
		int endingAdd = dbMap.get(p2);
		int originAdd = dbMap.get(p1);
		
		int edgeToPtr = endingAdd;
		
		while (edgeToPtr != originAdd)
		{
			//System.out.println(edgeToPtr);
			int edgeFromIdx = edgeTo[edgeToPtr];
			//System.out.println(edgeFromIdx);
			
			if (edgeFromIdx == -1)
			{
				System.out.println("Exception: no-link has been found.");
				result.clear();
				return result;
				//return null;
				
			}
			else
			{
				Person edgeFrom = dbPpl[edgeFromIdx];
				stk.push(edgeFrom);
				
				edgeToPtr = edgeFromIdx;
			}
			
		}
		if (stk.isEmpty() == true)
		{
			System.out.println("Bug Report: the Stack is empty");
		}
		
		while (stk.isEmpty() != true)
		{
			result.add(stk.pop().name);
		}
		
	
		
		/** COMPLETE THIS METHOD **/
		
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
		return result;
	}

	
	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null or empty array list if there is no student in the
	 *         given school
	 */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) 
	{
		
		
		
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		
		if (g == null)
		{
			System.out.println("Exception: the graph is null. check its building impletation or its data");
			result.clear();
			return result;
			//return null;
		}
		
		HashMap<String, Integer> dbMap = g.map;
		int dbSize = dbMap.size();
		Person[] dbPpl = g.members;


		ArrayList<Person> colleagues = new ArrayList<Person>();
		
		boolean[] isInserted = new boolean[dbSize]; //an boolean array to check students are already checked and inserted in a island already.
		
		boolean valid = false;
		
		Person start = null;
		
		//System.out.println(school);
		for (String k : dbMap.keySet()) // loop for validity checking 
		{
			//System.out.println("loop check");
			int add = dbMap.get(k);
			Person temp = dbPpl[add];
			
			//System.out.println(temp.name + " " + temp.school);
			
			if ((temp.student == true) && (temp.school.equals(school)))
			{
				//System.out.println("loop check");
				valid = true;
				start = temp;
				colleagues.add(temp);
				
			}
		}
		
		if (valid == false)// validity checking
		{
			//throw exception
			System.out.println("Exception: Invalid school name. There is no person who attend to the input school");
			return null;
		}
		else if (start == null)
		{
			//throw Bug Report
			System.out.println("Bug Report: Although it is valid to start, the starting person is null");
			return null;
		}
		else if (colleagues.isEmpty() == true)
		{
			//throw Bug Report
			System.out.println("Bug Report: Although it is valid to start, the colleague arraylist is empty");
		}
		else //start finding its cliques island
		{
			//int startAdd = dbMap.get(start.name);
			//isInserted[startAdd] = true;	
			
			Queue<Person> q = new Queue<Person>();
			//Stack<Person> stk = new Stack<Person>();
			
			boolean[] isMarked = new boolean[dbSize];
			int[] edgeTo = new int[dbSize];
			
			for (int i = 0 ; i < edgeTo.length; i ++) 
			{
				edgeTo[i] = -1;
			}
	
			//loop start
			for (int i = 0; i < colleagues.size(); i++)//outer loop for colleagues 
			{
				Person student = (colleagues.get(i));
				int stdAdd = dbMap.get(student.name);
				
				if (isInserted[stdAdd] == true)
				{
					System.out.println(student.name + " is already inserted, move to next student.");
				}
				else if (isInserted[stdAdd] == false)
				{
					ArrayList<String> island = new ArrayList<String>();
					q.enqueue(student);
					isMarked[stdAdd] = true;
					
					while (q.isEmpty() != true) //BFS start, I actually wanna put this function in different method.. for modulization/better readilbiltity.
					{
						Person psn = q.dequeue();
						int dQPsnAdd = dbMap.get(psn.name);
						isInserted[dQPsnAdd] = true;
						island.add(psn.name);
						
						if (isMarked[dQPsnAdd] == false)
						{
							//throw exception
							System.out.println("Exception: dequeued person data from the Q is not marked. "
									+ "whenever enquing a person data, it should be marked");
							return null;
						}
						else// start the loop inputing its friend.
						{
							Friend ptr = psn.first;
							while(ptr != null)
							{
								int ptrAdd = ptr.fnum;
								Person ptrPerson = dbPpl[ptrAdd];
								String ptrSchool = ptrPerson.school;
								System.out.println(ptrPerson.name + " " + ptrSchool);
								
								if (ptrSchool == null)
								{
									//just dont do anything....
									//I need this if statement because String.equals(school) will have nullpointer exception when ptrSchool is null
								}
								else if (isMarked[ptrAdd] == false && ptrSchool.equals(school))
								{
									isMarked[ptrAdd] = true;
									q.enqueue(ptrPerson);
									edgeTo[ptrAdd] = dQPsnAdd; 
								}
								ptr = ptr.next;
								//should not break it, because there could be a friend whose school is the given school later..... (linked list)
							}//friend while done
						}
					}// enQ/dQ while done
					
					result.add(island);
				}//if done
			}
		}
		
		/** COMPLETE THIS METHOD **/
		
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
		return result;
		
	}
	
	//private static int[] cntrEdgeTo = null; //이렇게 해도 되나....?
	//private static boolean[] cntrIsMarked = null;
	
	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null or empty array list if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) 
	{
		ArrayList<String> result = new ArrayList<String>();
		
		if (g == null)
		{
			System.out.println("Exception: the graph is null. check its building impletation or its data");
			result.clear();
			return result;
			//return null;
		}
		
		HashMap<String, Integer> dbMap = g.map;
		Person[] dbPpl = g.members;
		int dbSize = dbMap.size();
		
		//Queue<Person> q = new Queue<Person>();
		//cntrIsMarked = new boolean[dbSize];
		//cntrEdgeTo = new int[dbSize];
		
		boolean[] isConnector = new boolean[dbSize];
		
		boolean[] isMarked = new boolean[dbSize];
		//System.out.println(isMarked.length);
		int[] edgeTo = new int[dbSize];
		//System.out.println(edgeTo.length);
		for (int i = 0 ; i < edgeTo.length; i ++) 
		{
			edgeTo[i] = -1;
		}
		
		int[] dfsnum = new int[dbSize];
		int[] back = new int[dbSize];
		
	
		
		//Assignment explanantion says use DFS. a person's friends are in a linked list.
		
		//Person starter = dbPpl[0];
		
		for (int i = 0; i < dbSize; i++)
		{
			
			if (isMarked[i] == false)
			{
				Person starter = dbPpl[i];
				int vistedIndicator[] = new int[1];
				vistedIndicator[0] = 0;
				System.out.println("DFS start at:" + dbPpl[i].name);
				dfs1(vistedIndicator, g, starter, starter, isMarked, isConnector, dfsnum, back);
			}
		}
		
		
		//DFS = stack / recursion.
		
		result = insertConnectors(isConnector, dbPpl);
			
		return result;
		
	}
	
	
	private static void dfs1(int visitNum[], Graph g, Person psn, Person start, boolean[] isMarked, boolean[] isConnector, int[] dfsnum, int[] back)
	{
		HashMap<String, Integer> dbMap = g.map;
		Person[] dbPpl = g.members;
		int psnAdd = dbMap.get(psn.name);
		//int startAdd = dbMap.get(start.name);
		
		if (isMarked[psnAdd] == false)
		{
			isMarked[psnAdd] = true;
			visitNum[0]++;
			dfsnum[psnAdd] = visitNum[0]; 	//This is the dfs number, assigned when a vertex is VISTED, dealt out in INCREASING order.
			back[psnAdd] = visitNum[0];		 //This is a number that is initially assigned when a vertex is VISITED,
										//and is EQUAL to dfsnum, but can be changed later
			
			System.out.println("Recursion starts at: " + psn.name + " " +dfsnum[psnAdd] + "/" + back[psnAdd]);
			System.out.println();
											 
		}
		else
		{
			System.out.println("Bug report- visited=true case found: the person has been visted already when a recursion call start");
		}
		
		
		Friend frnd = psn.first;
		
		while(frnd != null) //이러면 연결되지 않은 friend network를 확인 할 수가 없음. for loop로 graph 전체를 iterate 해야할듯함. <- 해결.
		{
			int frndAdd = frnd.fnum;
			Person friend = dbPpl[frndAdd];
			
			if(isMarked[frndAdd] == true)//if a neighbor(frnd) is already VISITED,
			{
				System.out.println("Current psn = " + psn.name + ", Current frnd = " + friend.name);
				System.out.println(friend.name + " is already visted, ");
				System.out.println("back[" + psn.name + "] = min[back(" + psn.name + ") = " + back[psnAdd] + ", dfsnum(" + friend.name + ") = " + dfsnum[frndAdd] + "]");
				back[psnAdd] = Math.min(back[psnAdd], dfsnum[frndAdd]); //if a neighbor(frnd) is already visited, then back(psn) = min(back(psn),dfsnum(frnd))
				System.out.println("back[" + psn.name +"] = " + back[psnAdd]);
				System.out.println();
			
			}
			else //if it is not visited
			{
				//edgeTo[frndAdd] = psnAdd;
				dfs1(visitNum, g, friend, start, isMarked, isConnector, dfsnum, back);
				
				if (dfsnum[psnAdd] > back[frndAdd]) //When the DFS backs up from a neighbor(recursion call/return back from child to mother one)
													// if dfsnum(psn) > back(frnd) then, set to minimum of (back[psn] and back[frnd])
				{
					System.out.println("Current psn = " + psn.name + ", Current frnd = " + friend.name);
					System.out.println("dfsnum[" + psn.name + "] > back[" + friend.name + "] => " + dfsnum[psnAdd] + " vs " + back[frndAdd]);
					System.out.println("back[" + psn.name + "] = min[back(" + psn.name + ") = " + back[psnAdd] + ", back(" + friend.name + ") = " + back[frndAdd] + "]");
					back[psnAdd] = Math.min(back[psnAdd], back[frndAdd]);
					System.out.println("back[" + psn.name +"] = " + back[psnAdd]);
					System.out.println();
				}
				else //if (dfsnum[psnAdd] <= back[frndAdd])
				{
					if (psn != start)// psn is identified as connector if psn is NOT the starting point
					{
						System.out.println("Current psn = " + psn.name + ", Current frnd = " + friend.name);
						System.out.println(psn.name + " is Connector (" + psn.name + " is not starter + dfsnum[" + psn.name + "] <= back[" + dbPpl[frnd.fnum].name + "])");
						isConnector[psnAdd] = true; //PSN IS CONNECTOR
					}
					else //if (psn == start) psn is starting point
					{
						if (vaildityChecker(dbPpl, psn, frndAdd, isConnector, isMarked, dfsnum, back) == true) //look at the line @504
						{
							System.out.println("Current psn = " + psn.name + ", Current frnd = " + friend.name);
							System.out.println(psn.name + "is Connector (" + psn.name + "is connector, but valid.)");
							isConnector[psnAdd] = true;
						}
						/*
						else// 디버그 체크용: psn이 starting point라는 뜻은, psn의 친구들의 back값이 무조건 1이여야 하는데, 아닌경우가 있음. 이런 경우는 버그.
						{
							System.out.println("Bug Report: all of starting point's friends must have 1 as their back(num)");
						}
						*/
							
					}
					
				}
			}
			frnd = frnd.next;
			
		}
		 
		
		
	}
	
	//starting point가 connector가 되기 위한 조건:
											//1. DFS가 Starter's friends 모두 다 visit 했을때
												// visit 하지 않았으면, 정보가 부족해 connector 인지 아닌지 알수가 없음
							 //(다른 가능성이 있는 조건):2-1 Starter의 children 숫자가 무조건 2개 이상이어야 함.
										//(현재 조건):2-2 dfsnum[givenFrndAdd] - back[givenFrndAdd]가 1,0 이면 invalid. displace가 2 이상 차이 나야 valid.
											//둘 다 성립해야 true, 하나라도 아니면 false.
	private static boolean vaildityChecker(Person[] dbPpl, Person psn, int givenFrndAdd, boolean[] isConnector, boolean[] isMarked, int[] dfsnum, int[] back) 
	{
		boolean validity = false;
		Friend frnd = psn.first;
		Person given = dbPpl[givenFrndAdd];
		System.out.println("validity check start with, psn = " + psn.name + ", frnd = " + given.name);
		
		while (frnd != null)
		{
			int frndAdd = frnd.fnum;
			if (isMarked[frndAdd] == false)
			{
				validity = false;
				break;
			}
			else //이쪽 부분만 수정 하면 될듯 <ㅡ 해결이 얼추 된거 같아 보임.
			{
				validity = true;
				//이때 경우가 더 있다는 말임
				//(현재 조건):2-2 dfsnum[givenFrndAdd] - back[givenFrndAdd]가 1,0 이면 invalid. displace가 2 이상 차이 나야 valid.
				if (dfsnum[givenFrndAdd] - back[givenFrndAdd] < 2) // i am not sure it is valid condition or not....
				{
					System.out.println(dfsnum[givenFrndAdd] + "/" + back[givenFrndAdd]);
					validity = false;
					break;
				}
				
			}
			frnd = frnd.next;
		}
		return validity;
	}
	
	private static ArrayList<String> insertConnectors (boolean[] isConnector, Person[] dbPpl)
	{
		ArrayList<String> result = new ArrayList<String>();
		
		for (int i = 0; i < isConnector.length; i++)
		{
			if (isConnector[i] == true)
			{
				result.add(dbPpl[i].name);
			}
		}
		
		return result;
	}
}

