package com.dev.campus.analyser;


import java_cup.runtime.*;
import java.io.*;
import java.util.ArrayList;

%%
%class Scanner

%{

  public int yyline() {
    return yyline;
  }

  public int yycolumn() {
    return yycolumn;
  }

%}

%line
%column
%cupsym MySymbol
%cup
%{
  private Symbol symbol (int type) {
    return new Symbol (type, yyline, yycolumn);
  }

  private Symbol symbol (int type, Object value) {
    return new Symbol (type, yyline, yycolumn, value);
  }
%}

Hours          = [0-9][0-9] /*[01]?[0-9] | 2[0-3]*/
Minutes      = [0-5][0-9]
Time       = {Hours}h
LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f]

%%
<YYINITIAL> {

/* -------------------------------------------------
  Times
   ------------------------------------------------- */

"de"      { return symbol(MySymbol.FROM, yytext()); }
[Aà]       { return symbol(MySymbol.AT, yytext()); }
{Time}     { System.out.println("TIME"); return symbol(MySymbol.TIME, yytext()); }


/* -------------------------------------------------
  Other
   ------------------------------------------------- */
{WhiteSpace}  { /* ignore */ }

.       { System.out.println("NOTHING"); /* Ignore other characters */ }

}
