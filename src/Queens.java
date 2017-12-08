import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Queens {

		int[] posicoesRainhas;
		char[][] tabuleiro;
		int n;
		
		public Queens(int k) {
			n = k;
			posicoesRainhas = new int[n];
			tabuleiro = new char[n][n];
		}

				
		public void DE(){
			
			long start = System.currentTimeMillis();
			int geracao = 0;
			float cr;
			Random r = new Random();
			int[] randomNum = new int[3];
			int[] u = new int[n];
			int k = 20;
			int v;
			int minLocal;
			posicoesRainhas = geraPosicoes();
			u = posicoesRainhas;
			int fim = calcularHeuristica(posicoesRainhas);
			System.out.println("Tabuleiro inicial, rainhas atacantes: "+ fim);
			imprimeTabuleiro();
			
			while(fim != 0 && geracao < 500000) {
				
				for(int i = 0; i < n; i++){
					randomNum = geraTresNum(i);
					v = (randomNum[0] + randomNum[2] - randomNum[1] + n)%n;
					cr = r.nextFloat();
					
					for(int j = 0; j < k; j++){
						if(cr < 0.4){
							u[i] = v;
						}
						else u[i] = posicoesRainhas[i];
						minLocal = calcularHeuristica(u);
						if(minLocal < fim){
							posicoesRainhas = u;
							fim  = calcularHeuristica(posicoesRainhas);
						}
					}
				}
				
				geracao++;
			}
			long elapsedTime = System.currentTimeMillis()-start;
			System.out.println("Tabuleiro final, rainhas atacantes: "+ fim);
			imprimeTabuleiro();
			System.out.println("Total gerações: "+ geracao+" \n");
			System.out.println("Tempo total: "+ elapsedTime +" ms");
		}
		
		
		private int[] geraTresNum(int pos) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			int[] vet = new int[3];
			int cont = 0;
	        for (int i = 0; i < n; i++) {
	            list.add(new Integer(i));
	        }
	        Collections.shuffle(list);
	        for (int i = 0; i < 3; i++){
	            if(list.get(cont) == pos){
	            	cont ++;
	        	}
	            vet[i] = list.get(cont); 
	            cont ++;
	        }
			return vet;
		}

		
		private int[] geraPosicoes() {

			List<Integer> randomPos = new ArrayList<Integer>();

			Random r = new Random();
			for (int i = 0; i < n; i++) {
				randomPos.add(r.nextInt(n));
			}

			int[] randomPosicoes = new int[n];

			for (int i = 0; i < randomPos.size(); i++) {
				randomPosicoes[i] = randomPos.get(i);
			}

			return randomPosicoes;
		}
		
		
		private void atualizaTabuleiro(){
			for(int coluna = 0; coluna < posicoesRainhas.length; coluna ++){
				for(int linha = 0; linha < posicoesRainhas.length; linha++){
					if(linha == posicoesRainhas[coluna]){
						tabuleiro[linha][coluna] = 'R';
					}
					else tabuleiro[linha][coluna]= '-';
				}
			}
		}
		
		
		public void imprimeTabuleiro() {
			
			atualizaTabuleiro();
			
			for(int i = 0; i < posicoesRainhas.length; i++){
				System.out.print(posicoesRainhas[i] +  " ");
			}
			System.out.println(" ");
			
			for (int coluna = 0; coluna < n; coluna++) {
				for (int linha = 0; linha < n; linha++) {
				System.out.print(tabuleiro[coluna][linha] + " ");	
				}
				System.out.println(" ");
			}
			System.out.println(" ");
		}
		
		
		public int calcularHeuristica(int [] vet) {
			int conflitos = 0;
			int linha1;
			int linha2;
			int coluna1;
			int coluna2;
			for(int i = 0; i < n; i++) {
				for(int j = i+1; j < n; j++) {
					linha1 = vet[i];
					linha2 = vet[j];
					coluna1 = i;
					coluna2 = j;
					if(linha1 == linha2){ 
						conflitos++;
					}
					else if (coluna1 == coluna2){
						conflitos++;
					}
					else if(Math.abs(linha1-linha2) == Math.abs(coluna1-coluna2)) {
						conflitos++;
					}		
				}
			}
			return conflitos;
		}

	
		public static void main(String[] args) {
			Queens rainhas = new Queens(8);
			rainhas.DE();
		}
		
}