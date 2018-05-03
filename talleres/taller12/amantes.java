 public static int  ataques(int[] queens) {
        int cuenta =0;
        for(int i = 0; i < queens.length; i++){
            for(int j = i+1; j < queens.length; j++){
                if(Math.abs(queens[i] - queens[j]) == Math.abs(i - j) || queens[i]==queens[j]){
                    cuenta++;
                }
            }
        }
        return cuenta;
    }
