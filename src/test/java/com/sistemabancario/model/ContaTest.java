package com.sistemabancario.model;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContaTest {

    @Test
    void testSetNumeroValido() {
        final Conta instance = new Conta();
        final String esperado = "12345-6";
        instance.setNumero(esperado);
        final String obtido = instance.getNumero();
        assertEquals(esperado, obtido);
    }

    @Test
    void testSetNumeroInvalidoNaoArmazena() {
        final Conta instance = new Conta();
        final String invalido = "123";
        assertThrows(IllegalArgumentException.class, () -> instance.setNumero(invalido));
        final String obtido = instance.getNumero();
        assertNotEquals(invalido, obtido);
    }

    @Test
    void testSetNumeroNulo() {
        final Conta instance = new Conta();
        assertThrows(NullPointerException.class, () -> instance.setNumero(null));
    }

    @Test
    void testInstanciaPadraoPoupanca() {
        final Conta instance = new Conta();
        assertFalse(instance.isPoupanca());
    }

    @Test
    void testSetLimiteContaEspecial() {
        final Conta instance = new Conta();
        instance.setEspecial(true);
        final double esperado = 1000;
        instance.setLimite(esperado);
        final double obtido = instance.getLimite();
        assertEquals(esperado, obtido);
    }

    @Test
    void testSetLimiteContNaoEspecial() {
        final Conta instance = new Conta();
        final double limite = 1000;
        assertThrows(IllegalStateException.class, () -> instance.setLimite(limite));
    }

    @Test
    void testHistoricoNotNull() {
        final Conta instance = new Conta();
        assertNotNull(instance.getMovimentacoes());
    }
    
    @Test
    void testAddMovimentacaoCredito(){
        final Conta instance = new Conta();
        final Movimentacao mov = new Movimentacao(instance);
        mov.setConfirmada(true);
        mov.setTipo('C');
        final double esperado = 100.50;
        mov.setValor(esperado);
        instance.addMovimentacao(mov);
        assertEquals(esperado, instance.getSaldoTotal());
    }
    
    void testAddMovimentacaoDebito(){
        final Conta instance = new Conta();
        final Movimentacao mov = new Movimentacao(instance);
        mov.setConfirmada(true);
        mov.setTipo('D');
        final double valor = 100.50;
        final double esperado = -valor;
        mov.setValor(valor);
        instance.addMovimentacao(mov);
        assertEquals(esperado, instance.getSaldoTotal());
    }

    @Test
    void testGetSaldoTotal() {
        final double limite = 500;
        final double esperado = limite;
        final Conta instance = new Conta();
        instance.setEspecial(true);
        instance.setLimite(limite);
        final double obtido = instance.getSaldoTotal();
        assertEquals(esperado, obtido);
    }

    @Test
    void testDepositoDinheiro() {
        final double limite = 500.6, deposito = 500.8, esperado = 1001.4;
        final Conta instance = new Conta();
        instance.setEspecial(true);
        instance.setLimite(limite);
        instance.depositoDinheiro(deposito);

        final double obtido = instance.getSaldoTotal();
        assertEquals(esperado, obtido, 0.001);
    }

    @Test
    void testDepositoDinheiroTipoCredito() {
        final char esperado = 'C';
        final Conta instance = new Conta();
        instance.depositoDinheiro(500);
        final List<Movimentacao> movimentacoes = instance.getMovimentacoes();
        final int indexUltimaMovimentacao = movimentacoes.size() - 1;
        final char obtido = movimentacoes.get(indexUltimaMovimentacao).getTipo();
        assertEquals(esperado, obtido);
    }

    @Test
    void testDepositoDinheiroConfirmMovimentacao() {
        final Conta instance = new Conta();
        instance.depositoDinheiro(500);
        final List<Movimentacao> movimentacoes = instance.getMovimentacoes();
        final int indexUltimaMovimentacao = movimentacoes.size() - 1;
        final boolean esperado = movimentacoes.get(indexUltimaMovimentacao).isConfirmada();
        assertTrue(esperado);
    }

    @Test
    void testDepositoDinheiroValorIncluidoMovimentacao() {
        final double esperado = 500;
        final Conta instance = new Conta();
        instance.depositoDinheiro(esperado);
        final List<Movimentacao> movimentacoes = instance.getMovimentacoes();
        final int indexUltimaMovimentacao = movimentacoes.size() - 1;
        final double obtido = movimentacoes.get(indexUltimaMovimentacao).getValor();
        assertEquals(esperado, obtido);
    }

    @Test
    void testDepositoDinheiroMovimentacaoIncluida() {
        final Conta instance = new Conta();
        final List<Movimentacao> movimentacoes = instance.getMovimentacoes();
        final int esperado = movimentacoes.size() + 1;
        instance.depositoDinheiro(500);
        final int obtido = movimentacoes.size();
        assertEquals(esperado, obtido);
    }
}
