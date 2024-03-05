package com.vemser.compilador.data.factory;

import com.vemser.compilador.dto.CompiladorDto;
import com.vemser.compilador.enums.Linguagem;

public class CompiladorDataFactory {
    private static final String CODIGO_VALIDO_JAVA = "public%20class%20Main%20%7B%0A%20%20%20%20public%20static%20void%20main%28String%5B%5D%20args%29%20%7B%0A%20%20%20%20%20%20%20%20System.out.println%28%22Vem%20Ser%20-%20Corre%C3%A7%C3%A3o%22%29%3B%0A%20%20%20%20%7D%0A%7D";
    private static final String CODIGO_VALIDO_JAVASCRIPT = "console.log%28%27Vem%20Ser%20-%20Corre%C3%A7%C3%A3o%27%29%3B";
    private static final String CODIGO_INVALIDO_JAVA = "Sistem.aut.print(\\\"Vem Ser - Correção\\\")";
    private static final String CODIGO_INVALIDO_JAVASCRIPT = "consoli.log('Vem Ser - Correção')";

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

    public static CompiladorDto compiladorCodigoJavaInvalido() {
        CompiladorDto compiladorCodigoInvalido = new CompiladorDto();
        compiladorCodigoInvalido.setCodigo(CODIGO_INVALIDO_JAVA);
        compiladorCodigoInvalido.setLinguagem(Linguagem.JAVA);
        return compiladorCodigoInvalido;
    }

    public static CompiladorDto compiladorCodigoJavascriptInvalido() {
        CompiladorDto compiladorCodigoInvalido = new CompiladorDto();
        compiladorCodigoInvalido.setCodigo(CODIGO_INVALIDO_JAVASCRIPT);
        compiladorCodigoInvalido.setLinguagem(Linguagem.JAVASCRIPT);
        return compiladorCodigoInvalido;
    }

    public static CompiladorDto compiladorLinguagemInvalida() {
        CompiladorDto compiladorLinguagemInvalida = new CompiladorDto();
        compiladorLinguagemInvalida.setCodigo(CODIGO_VALIDO_JAVA);
        compiladorLinguagemInvalida.setLinguagem(Linguagem.PYTHON);
        return compiladorLinguagemInvalida;
    }

    public static CompiladorDto compiladorCodigoNulo() {
        CompiladorDto compiladorCodigoNulo = new CompiladorDto();
        compiladorCodigoNulo.setCodigo(null);
        compiladorCodigoNulo.setLinguagem(Linguagem.JAVA);
        return compiladorCodigoNulo;
    }

    public static CompiladorDto compiladorLinguagemNula() {
        CompiladorDto compiladorLinguagemNula = new CompiladorDto();
        compiladorLinguagemNula.setCodigo(CODIGO_VALIDO_JAVA);
        compiladorLinguagemNula.setLinguagem(null);
        return compiladorLinguagemNula;
    }

}
