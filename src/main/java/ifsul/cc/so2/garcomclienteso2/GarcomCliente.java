package ifsul.cc.so2.garcomclienteso2;

import static java.lang.Thread.sleep;

/**
 *
 * @author Mauro
 */
public class GarcomCliente {

    public static void main(String[] args) {
        Bar b = new Bar();  // cria o bar
        Garcom p1 = new Garcom(b, 1); // cria o garçom
        Cliente c1 = new Cliente(b, 1); // cria o cliente 
        p1.start(); // inicia o garçom
        c1.start(); // inicia o cliente
    }
}

class Bar {

    private int cerveja; // define o produto
    private boolean disponivel = false; //define se esta disponivel ou nao 

    public synchronized int get() {
        while (disponivel == false) { //se nao estiver disponivel, entra em espera
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        disponivel = false;
        notifyAll();
        return cerveja; // retorna o produto
    }

    public synchronized void servir(int value) { // metodo servir 
        while (disponivel == true) { // se tiver disponivel, entra em espera
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        cerveja = value; // retorna a quantidade do produto
        disponivel = true; // se tiver disponivel, serve
        notifyAll();
    }
}

class Cliente extends Thread { // classe cliente, extende thread

    private Bar bar; // atributo bar 
    private int numero; // atributo numero 

    public Cliente(Bar b, int number) {
        bar = b;
        this.numero = numero;
    }

    public void run() { //metodo run 
        int value = 0; // inicia o valor em 0
        for (int i = 0; i < 10; i++) {
            value = bar.get();
            System.out.println("Cliente Bebe: " + value); //retorna a quantidade que o cliente bebeu
        }
    }
}

class Garcom extends Thread { //classe garçom, extende thread

    private Bar bar; //atributo bar
    private int numero; //atributo numero

    public Garcom(Bar b, int number) {
        bar = b;
        this.numero = numero;
    }

    public void run() { //metodo run
        for (int i = 0; i < 10; i++) {
            bar.servir(i);
            System.out.println("Garçom Serve: " + i); //retorna a quantidade que o garçom serviu
            try {
                sleep((int) (Math.random() * 100)); //define um numero aleatorio e fica esperando
            } catch (InterruptedException e) {
            }
        }
    }
}
