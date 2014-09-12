/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bestn;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author charleshenriqueportoferreira
 */
public class BestN {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String nomeArquivo = args[0];
        String arquivoModificicado = "";
        int best[] = new int[]{2, 4, 6, 8, 10, 12, 15, 20, 22, 30};
        for (int j : best) {
            try {
                arquivoModificicado = lerArquivo(nomeArquivo, args[2], j);
            } catch (IOException ex) {
                Logger.getLogger(BestN.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                printFile(args[1] + "_" + j+ ".csv", arquivoModificicado);
            } catch (IOException ex) {
                Logger.getLogger(BestN.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static String lerArquivo(String filePath, String valor, int qtde) throws FileNotFoundException, IOException {
        StringBuilder linha = new StringBuilder();
        ArrayList<String[]> genesSelecionados = new ArrayList<>();
        ArrayList<String[]> genes = new ArrayList<>();
        double max = 20.0;
        double min = 16000.0;
        try (FileReader fr = new FileReader(filePath); BufferedReader br = new BufferedReader(fr)) {
            while (br.ready()) {
                String linhaLida = br.readLine();

                if (!linhaLida.contains("SNO")) {
                    String arrayChars[] = linhaLida.split(";");
                    genes.add(arrayChars);
                } else {
                    //linha.append(linhaLida);
                    //linha.append("\n");
                }

            }
            br.close();
            fr.close();
            int indiceMelhorGene = 0;

            int indiceTvalue = Integer.parseInt(valor);

            for (int j = 0; j < qtde; j++) {
                max = -20.0;
                indiceMelhorGene = 0;
                for (int i = 0; i < genes.size(); i++) {
                    try {
                        String valorExtraido = genes.get(i)[indiceTvalue];
                        double t_value = Double.parseDouble(valorExtraido);
                        if (t_value > max) {
                            indiceMelhorGene = i;
                            max = t_value;
                        }

                        // int numero = Integer.parseInt(arrayChars[i]);
                    } catch (NumberFormatException e) {
                        int b = 0;
                    }
                }
                genesSelecionados.add(genes.get(indiceMelhorGene));
                genes.remove(indiceMelhorGene);
            }
        }

        for (String[] strings : genesSelecionados) {
            linha.append(String.join(",", strings)).append('\n');
        }

        return linha.toString();
    }

    public static void printFile(String fileName, String texto) throws IOException {
        try (FileWriter fw = new FileWriter(fileName); BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(texto);
            bw.newLine();
            bw.close();
            fw.close();
        }
    }

}
