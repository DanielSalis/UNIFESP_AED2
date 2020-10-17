/*
Trabalho de Ordenação Externa
Matéria: Algoritmo e Estrutura de Dados 2
Professor: Alvaro L. Fazenda.
2020

Alunos:
- Matheus Gomes de Paula, RA: 122.043
- Daniel Salis, RA: 123.145

Descrição:
Implemente um algoritmo de Ordenação Externa de sua excolha (Intercalação Balanceada,
Seleção por substituição ou Intercalação Polifásica). Utilize os arquivos passados como anexos
como dados de entrada (arq15M.txt, arq40M.txt), o qual fornece dados do tipo long int, e
gere um arquivo de saida ordenado. Utilize apenas duas fitas/arquivos como temporários
na fase de intercalação. Considere as duas seguintes situações para capacidade da memória
interna:
a) 1000000 registros
b) 10000000 registros
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;

import static java.lang.System.*;

public class Main {
    public String _outputFileName;
    public static int _numFiles = 2;
    public int _sizeFile;
    public static int _case1 = 1000000;
    public static int _case2 = 10000000;
    public int emptyFiles = 0;

    public void start() {
        try {
            // Casos de capacidade de memoria
            int[] sizes = new int[2];
            sizes[0] = _case1;
            sizes[1] = _case2;

            // Arquivos a serem lidos
            String[] files = new String[2];
            files[0] = "arq15M.txt";
            files[1] = "arq40M.txt";

            // 4 Casos possíveis
            for (int size : sizes) {
                for (String file : files) {
                    // Tamanho e nome
                    this._sizeFile = size;
                    this._outputFileName = "output_" + size;

                    // Cria os arquivos
                    this.createFiles(file);
                }
            }
        } catch (Exception ex) {
            out.println(ex.getMessage());
        }
    }

    public void createFiles(String fileInput) throws IOException {
        // criando os arquivos temporarios com nomes sequenciais e
        // escrevendo os valores ordenados dentro de cada um
        long start = nanoTime();
        ArrayList<FileWriter> outputs = new ArrayList<>();
        {
            int i = 0;
            while (i < _numFiles) {
                outputs.add(new FileWriter("file_" + i + ".txt"));
                i++;
            }
        }
        readFile(fileInput, outputs);
        long end = nanoTime();
        long elapsedTime = end - start;
        out.println(_outputFileName + ": (nanosec) " + elapsedTime);

        // merge dos arquivos e ja é passado o buffer de leitura
        start = nanoTime();
        List<BufferedReader> files = new ArrayList<>();
        {
            int i = 0;
            while (i < _numFiles) {
                FileReader fileReader = new FileReader("file_" + i + ".txt");
                files.add(new BufferedReader(fileReader));
                i++;
            }
        }
        mergeFiles(files);
        end = nanoTime();
        elapsedTime = end - start;
        out.println(_outputFileName + ": (nanosec) " + elapsedTime);

        int i = 0;
        while (i < _numFiles) {
            files.get(i).close();
            i++;
        }
    }

    public void readFile(String filename, List<FileWriter> files) throws IOException {
        _outputFileName += "_" + filename;
        FileReader fileReader;
        try {
            fileReader = new FileReader(filename);
        } catch (FileNotFoundException ex) {
            throw ex;
        }

        BufferedReader buffer = new BufferedReader(fileReader);

        String line = buffer.readLine();
        boolean stop = false;
        int i = 0;
        while (stop != true && i < _numFiles) {
            long[] array = new long[this._sizeFile];
            int j;
            // Preenche um array com o tamanho maximo definido para cada arquivo
            for (j = 0; j < this._sizeFile; j++) {
                array[j] = Long.parseLong(line);
                line = buffer.readLine();
                if (line == null) {
                    stop = true;
                    break;
                }
            }

            quickSort(array, 0, j - 1);

            // Escreve todo o array no arquivo e pula para o proximo arquivo
            FileWriter file = files.get(i);
            int k = 0;
            while (k < j) {
                file.write(array[k] + "\n");
                k++;
            }
            file.close();
            i++;
        }
        buffer.close();
        fileReader.close();
    }

    public void quickSort(long[] list, int low, int high) {
        if (low < high) {
            int partitioningIndex = partition(list, low, high);
            quickSort(list, low, partitioningIndex - 1);
            quickSort(list, partitioningIndex + 1, high);
        }
    }

    public int partition(long[] list, int low, int high) {
        int middle = (low + high) / 2;
        long listLow = list[low];
        long listMiddle = list[middle];
        long listHigh = list[high];

        int index;
        if (listLow < listMiddle) {
            if (listMiddle < listHigh)
                index = middle;
            else {
                if (listLow < listHigh)
                    index = high;
                else
                    index = low;
            }
        } else {
            if (listHigh < listMiddle)
                index = middle;
            else {
                if (listHigh < listLow)
                    index = high;
                else
                    index = low;
            }
        }

        swap(list, index, high);

        long x = list[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (list[j] <= x) {
                i++;
                swap(list, i, j);
            }
        }

        swap(list, i + 1, high);
        return (i + 1);
    }

    public void swap(long[] list, int i, int j) {
        long listI = list[i];
        long listJ = list[j];

        list[i] = listJ;
        list[j] = listI;
    }

    public int getMin(long[] list) {
        int index = 0;
        for (int i = 1; i < _numFiles; i++) {
            if (list[i] < list[i - 1]) {
                index = i;
            }
        }
        return index;
    }

    public void mergeFiles(List<BufferedReader> files) throws IOException {
        FileWriter file = new FileWriter(this._outputFileName);

        // Atribiu a primeira posição de cada arquivo para a variável mixed

        int i = 0;

        long[] mixed = new long[_numFiles];
        for (i = 0; i < _numFiles; i++) {
            BufferedReader buffer = files.get(i);
            mixed[i] = Long.parseLong(buffer.readLine());
        }

        // Dentro do Laço de repetição é obtido o menor valor do vetor e o mesmo é
        // escrito no final. Depois sua posição é trocada com o próximo buffer
        while (this.emptyFiles < _numFiles) {
            int j = getMin(mixed);
            if (mixed[j] != -1) {
                file.write(mixed[j] + "\n");
                String line = files.get(j).readLine();

                if (line != null) {
                    mixed[j] = Long.parseLong(line);
                } else {
                    mixed[j] = -1;
                }

            } else
                this.emptyFiles++;
        }
        this.emptyFiles = 0;
        file.close();
    }

    public static void main(String[] args) {
        Main main = new Main();

        main.start();
    }
}