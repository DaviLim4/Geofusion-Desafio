import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class DesafioGeofusion {

    public static void main(String[] args) throws InterruptedException {


        System.out.println("Olá");

        buscarDados();
    }


    public static void buscarDados() throws InterruptedException {
        Estabelecimento[] listaEstabelecimento = new Estabelecimento[10];
        String nomeArquivo;
        Scanner leitorDiretorio = new Scanner(System.in);

        System.out.println("Digite o diretório e nome do arquivo (com a extensão) onde o CSV será criado: Ex:(C:/Users/Pasta1/Desktop/PASTAS/teste.csv");
        nomeArquivo = leitorDiretorio.next();

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\DaviLima\\Desktop\\driver\\chromedriver.exe");
        WebDriver navegador = new ChromeDriver();
        navegador.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);//tempo de espera para o item ser encontrado

        navegador.get("http://cnes.datasus.gov.br/pages/estabelecimentos/consulta.jsp");
        navegador.manage().window().maximize();


        // Abre a combobox "ESTADO"
        navegador.findElement(By.cssSelector("body > div.layout > main > div > div.col-md-12.ng-scope > div > form:nth-child(4) > div:nth-child(2) > div:nth-child(1) > div > select")).click();

        //Seleciona a opção "ACRE"
        Select selecionaEstado = new Select(((ChromeDriver) navegador).findElementByCssSelector("body > div.layout > main > div > div.col-md-12.ng-scope > div > form:nth-child(4) > div:nth-child(2) > div:nth-child(1) > div > select"));
        selecionaEstado.selectByVisibleText("ACRE");


        // Abre a combobox "Municipio"
        navegador.findElement(By.cssSelector("body > div.layout > main > div > div.col-md-12.ng-scope > div > form.form-inline.ng-valid.ng-dirty.ng-valid-parse > div:nth-child(2) > div:nth-child(2) > div > select")).click();

        //Seleciona a opção ""
        Select selecionaMunicipio = new Select(((ChromeDriver) navegador).findElementByCssSelector("body > div.layout > main > div > div.col-md-12.ng-scope > div > form.form-inline.ng-valid.ng-dirty.ng-valid-parse > div:nth-child(2) > div:nth-child(2) > div > select"));
        selecionaMunicipio.selectByVisibleText("CAPIXABA");


        //Clica no botão pesquisar
        navegador.findElement(By.cssSelector("body > div.layout > main > div > div.col-md-12.ng-scope > div > form:nth-child(5) > div > button")).click();


        //Abrir popup "Detalhes"
        for (int i = 1; i <= 9; i++) {


            navegador.findElement(By.xpath("/html/body/div[2]/main/div/div[2]/div/div[3]/table/tbody/tr[" + i + "]/td[8]/button")).click();
            // navegador.findElement(By.cssSelector("body > div.layout > main > div > div.col-md-12.ng-scope > div > div:nth-child(9) > table > tbody > tr:nth-child(" + Integer.toString(i) + ") > td:nth-child(8) > button")).click();
            Estabelecimento obEstabelecimento = new Estabelecimento();
            Thread.sleep(4500);// Criado para dar o tempo necessário de carrgar os dados de busca na tela antes da proxima execução

            //Pegar os valores e armazenar.
            String txtNome = navegador.findElement(By.id("nome")).getAttribute("value");
            obEstabelecimento.setNome(txtNome);

            String txtCnes = navegador.findElement(By.id("cnes")).getAttribute("value");
            obEstabelecimento.setCnes(txtCnes);

            String txtCnpj = navegador.findElement(By.xpath("(//*[@id='cnpj'])")).getAttribute("value");
            obEstabelecimento.setCnpj(txtCnpj);

            String txtNomeEmpresarial = navegador.findElement(By.xpath("(//*[@id='cnpj'])[2]")).getAttribute("value");
            obEstabelecimento.setNomeEmpresarial(txtNomeEmpresarial);

            String txtLogradouro = navegador.findElement(By.xpath("(//*[@id='cnpj'])[3]")).getAttribute("value");
            obEstabelecimento.setLogradouro(txtLogradouro);

            String txtNum = navegador.findElement(By.xpath("(//*[@id='cnpj'])[4]")).getAttribute("value");
            obEstabelecimento.setNum(txtNum);

            String txtComplemento = navegador.findElement(By.xpath("(//*[@id='cnpj'])[5]")).getAttribute("value");
            obEstabelecimento.setComplemento(txtComplemento);

            String txtBairro = navegador.findElement(By.xpath("(//*[@id='cnpj'])[6]")).getAttribute("value");
            obEstabelecimento.setBairro(txtBairro);

            String txtMunicipio = navegador.findElement(By.xpath("(//*[@id='cnpj'])[7]")).getAttribute("value");
            obEstabelecimento.setMunicipio(txtMunicipio);

            String txtUf = navegador.findElement(By.xpath("(//*[@id='cnpj'])[8]")).getAttribute("value");
            obEstabelecimento.setUf(txtUf);

            String txtCep = navegador.findElement(By.xpath("(//*[@id='cnpj'])[9]")).getAttribute("value");
            obEstabelecimento.setCep(txtCep);

            String txtTel = navegador.findElement(By.xpath("(//*[@id='tel'])")).getAttribute("value");
            obEstabelecimento.setTel(txtTel);

            listaEstabelecimento[i] = obEstabelecimento; //Armazerna valores na lista de estabelecimentos

            //Clicar botão "Fechar"
            navegador.findElement(By.cssSelector("#dadosBasicosModal > div > div > div.modal-footer > button")).click();
            Thread.sleep(2500);// Criado para dar o tempo necessário de carrgar os dados de busca na tela antes da proxima execução

        }
        try {
          //  FileWriter escreve = new FileWriter(nomeArquivo);
            PrintWriter wrt = new PrintWriter(new File(nomeArquivo));
            StringBuilder sb = new StringBuilder();
            sb.append("Nome");
            sb.append(',');
            sb.append("CNES");
            sb.append(',');
            sb.append("CNPJ");
            sb.append(',');
            sb.append("Nome Empresarial");
            sb.append(',');
            sb.append("Logradouro");
            sb.append(',');
            sb.append("Numero");
            sb.append(',');
            sb.append("Complemento");
            sb.append(',');
            sb.append("Bairro");
            sb.append(',');
            sb.append("Municipio");
            sb.append(',');
            sb.append("UF");
            sb.append(',');
            sb.append("CEP");
            sb.append(',');
            sb.append("Telefone");
            sb.append('\n');

            for (int j = 1; j <= 9; j++) {

                sb.append(listaEstabelecimento[j].getNome());
                sb.append(',');
                sb.append(listaEstabelecimento[j].getCnes());
                sb.append(',');
                sb.append( listaEstabelecimento[j].getCnpj());
                sb.append(',');
                sb.append(listaEstabelecimento[j].getNomeEmpresarial());
                sb.append(',');
                sb.append(listaEstabelecimento[j].getLogradouro());
                sb.append(',');
                sb.append(listaEstabelecimento[j].getNum());
                sb.append(',');
                sb.append(listaEstabelecimento[j].getComplemento());
                sb.append(',');
                sb.append(listaEstabelecimento[j].getBairro());
                sb.append(',');
                sb.append( listaEstabelecimento[j].getMunicipio());
                sb.append(',');
                sb.append(listaEstabelecimento[j].getUf());
                sb.append(',');
                sb.append(listaEstabelecimento[j].getCep());
                sb.append(',');
                sb.append(listaEstabelecimento[j].getTel());
                sb.append('\n');


            }
            wrt.write(sb.toString());
            wrt.close();
            System.out.println("Arquivo CSV criado no diretório configurado!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        navegador.close();
    }
}
