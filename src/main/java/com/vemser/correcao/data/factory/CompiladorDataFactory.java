package com.vemser.correcao.data.factory;

import com.vemser.correcao.dto.CompiladorDto;
import com.vemser.correcao.enums.Linguagem;

public class CompiladorDataFactory {
    private static final String CODIGO_VALIDO_JAVA = "public%20class%20Main%20%7B%0A%20%20%20%20public%20static%20void%20main%28String%5B%5D%20args%29%20%7B%0A%20%20%20%20%20%20%20%20System.out.println%28%22Vem%20Ser%20-%20Corre%C3%A7%C3%A3o%22%29%3B%0A%20%20%20%20%7D%0A%7D";
    private static final String CODIGO_VALIDO_JAVASCRIPT = "console.log%28%27Vem%20Ser%20-%20Corre%C3%A7%C3%A3o%27%29%3B";
    private static final String CODIGO_INVALIDO = "Sistem.aut.print(\\\"Vem Ser - Correção\\\")";

    public static CompiladorDto compiladorJavaValido() {
        CompiladorDto compiladorJava = new CompiladorDto();
        compiladorJava.setCodigo(CODIGO_VALIDO_JAVA);
        compiladorJava.setLinguagem(Linguagem.JAVA);
        return compiladorJava;
    }

    public static CompiladorDto compiladorJavascriptValido() {
        CompiladorDto compiladorJavascript = new CompiladorDto();
        compiladorJavascript.setCodigo(CODIGO_VALIDO_JAVASCRIPT);
        compiladorJavascript.setLinguagem(Linguagem.JAVASCRIPT);
        return compiladorJavascript;
    }

    public static CompiladorDto compiladorCodigoInvalido() {
        CompiladorDto compiladorCodigoInvalido = new CompiladorDto();
        compiladorCodigoInvalido.setCodigo(CODIGO_INVALIDO);
        compiladorCodigoInvalido.setLinguagem(Linguagem.JAVA);
        return compiladorCodigoInvalido;
    }

    public static CompiladorDto compiladorLinguagemInvalida() {
        CompiladorDto compiladorLinguagemInvalida = new CompiladorDto();
        compiladorLinguagemInvalida.setCodigo(CODIGO_VALIDO_JAVA);
        compiladorLinguagemInvalida.setLinguagem(Linguagem.PYTHON);
        return compiladorLinguagemInvalida;
    }
}
