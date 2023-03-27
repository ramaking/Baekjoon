package m3.day0315;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_3176_2 {
	static int N;
	static ArrayList<int[]>[] list;
	static int[] depth;
	static boolean[] visited;
	static int[][] dp;
	static int[][][] distance;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		list = new ArrayList[N+1];
		depth = new int[N+1];
		visited = new boolean[N+1];
		dp = new int[N+1][30];
		distance = new int[N+1][30][2];
		for(int i = 1; i <= N; i++) {
			list[i] = new ArrayList<>();
		}
		StringTokenizer st;
		for(int i = 1; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int dis = Integer.parseInt(st.nextToken());

			list[from].add(new int[] {to, dis});
			list[to].add(new int[] {from, dis});
		}
		
		dfs(1, 0);
		
		
		calc();
		
		
		
		int M = Integer.parseInt(br.readLine());
		
		
		
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int dest = lca(u, v);
//			System.out.println(dest);
			int[] dis1 = getDis(u, dest);
			int[] dis2 = getDis(v, dest);
			sb.append(Math.min(dis1[1], dis2[1]))
			.append(" ")
			.append(Math.max(dis1[0], dis2[0]))
			.append("\n");

		}
		
		System.out.println(sb.toString());
		
	}
	
	
	private static int[] getDis(int u, int dest) {
		int max = 0;
		int min = Integer.MAX_VALUE;
		int current = u;
		int diff = depth[current] - depth[dest];
		
		for(int i = 0; diff != 0; i++) {
			if((diff & 1) == 1) {
				max = Math.max(max, distance[current][i][0]);
				min = Math.min(min, distance[current][i][1]);
//				sum += distance[current][i];
				current = dp[current][i];
				
			}
			diff >>= 1;
		}
		return new int[] {max, min};
	}


	private static int lca(int u, int v) {
		if(depth[u] < depth [v]) {
			int temp = u;
			u = v;
			v = temp;
		}
		
		int diff = depth[u] - depth[v];
		
		for(int i = 0; diff != 0; i++) {
			if((diff & 1) == 1) {
				u = dp[u][i];
			}
			diff >>= 1;
		}
		
		if(u == v) return u;
		
		for(int i = 29; i >= 0; i--) {
			if(dp[u][i] != dp[v][i]) {
				u = dp[u][i];
				v = dp[v][i];
			}
		}
		
		return dp[u][0];
		
		
	}


	private static void calc() {
		for(int j = 1; j < 30; j++) {
			for(int i = 1; i <= N; i++) {
				dp[i][j] = dp[dp[i][j-1]][j-1];
				distance[i][j][0] = Math.max(distance[i][j-1][0], distance[dp[i][j-1]][j-1][0]);
				distance[i][j][1] = Math.min(distance[i][j-1][1], distance[dp[i][j-1]][j-1][1]);
			}
		}
	}
	
	
	
	private static void dfs(int index, int depthNum) {
		visited[index] = true;
		depth[index] = depthNum;
		for(int i = 0; i < list[index].size(); i++) {
			if(!visited[list[index].get(i)[0]]) {
				distance[list[index].get(i)[0]][0][0] = list[index].get(i)[1];
				distance[list[index].get(i)[0]][0][1] = list[index].get(i)[1];
				dp[list[index].get(i)[0]][0] = index;
				dfs(list[index].get(i)[0], depthNum+1);
			}
			
		}
		
	}
}
