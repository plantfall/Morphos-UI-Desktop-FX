# Sistema de Tema Global

Este sistema permite definir estilos globais para seus componentes declarativos sem depender de arquivos CSS. Todos os estilos são aplicados programaticamente através de Java.

## Componentes Principais

### 1. MaterialTheme

Classe singleton que define todas as cores, tipografias e espaçamentos do tema baseado em Material Design.

```java
// Obter instância do tema
MaterialTheme theme = MaterialTheme.getInstance();

// Personalizar cores (opcional)
theme.setPrimaryColor(Color.web("#FF5722"))
     .setSecondaryColor(Color.web("#4CAF50"))
     .setFontFamily("Segoe UI");
```

### 2. ThemeStyler

Utilitário para aplicar estilos do tema aos componentes.

```java
// Aplicar estilos tipográficos
ThemeStyler.h1(label);        // Cabeçalho H1
ThemeStyler.h2(label);        // Cabeçalho H2
ThemeStyler.h3(label);        // Cabeçalho H3
ThemeStyler.body(text);       // Texto do corpo
ThemeStyler.caption(text);    // Legenda

// Aplicar cores
ThemeStyler.primary(label);    // Cor primária
ThemeStyler.secondary(label);  // Cor secundária
ThemeStyler.error(label);      // Cor de erro
ThemeStyler.onBackground(label); // Cor sobre fundo

// Aplicar estilos de componentes
ThemeStyler.textField(textField);  // Estilo de campo de texto
ThemeStyler.buttonStyle(button);   // Estilo de botão
ThemeStyler.card(container);       // Estilo de card
ThemeStyler.container(container);  // Estilo de container
```

## Uso nos Componentes Declarativos

### Text\_

```java
new Text_("Título", modifier -> modifier
    .h1()                    // Aplica estilo H1
    .styles().primary()      // Aplica cor primária
);
```

### Label\_

```java
new Label_("Subtítulo", modifier -> modifier
    .h2()                    // Aplica estilo H2
    .styles().secondary()    // Aplica cor secundária
);
```

### TextField\_

```java
new TextField_("Placeholder", modifier -> modifier
    .applyTheme()            // Aplica tema completo
    .marginTop(10)          // Adiciona margem
);
```

### Button\_

```java
new Button_("Clique Aqui", modifier -> modifier
    .marginTop(20).styles()
        .bgColor(theme.getPrimaryColor())      // Cor de fundo primária
        .textColor(theme.getOnPrimaryColor())  // Cor do texto sobre primária
        .borderRadius((int) theme.getBorderRadius()) // Raio da borda
);
```

## Cores Disponíveis

- **Primárias**: `primaryColor`, `primaryVariantColor`
- **Secundárias**: `secondaryColor`, `secondaryVariantColor`
- **Fundo**: `backgroundColor`, `surfaceColor`
- **Texto**: `onPrimaryColor`, `onSecondaryColor`, `onBackgroundColor`, `onSurfaceColor`
- **Estado**: `errorColor`, `disabledColor`, `focusColor`, `hoverColor`

## Tipografias Disponíveis

- **Cabeçalhos**: `h1()`, `h2()`, `h3()`, `h4()`, `h5()`, `h6()`
- **Corpo**: `body()`, `caption()`, `button()`
- **Pesos**: `headingFontWeight`, `bodyFontWeight`, `buttonFontWeight`

## Espaçamentos

- **Unidade base**: 8px (configurável)
- **Métodos**: `spacing(1)`, `spacing(2)`, etc.
- **Bordas**: `borderRadius`, `borderWidth`

## Exemplo Completo

```java
public class MinhaTela extends Column {
    public MinhaTela() {
        super((modifier) -> {
            modifier.padding(20);

            // Aplicar tema ao container
            ThemeStyler.container(this);

            // Título principal
            new Label_("Minha Aplicação", modifier -> modifier
                .h1().styles().primary()
            );

            // Subtítulo
            new Label_("Descrição da aplicação", modifier -> modifier
                .h3().styles().onBackground()
            );

            // Campo de entrada
            new TextField_("Digite algo...", modifier -> modifier
                .applyTheme().marginTop(20)
            );

            // Botão
            new Button_("Enviar", modifier -> modifier
                .marginTop(20).styles()
                    .bgColor(MaterialTheme.getInstance().getPrimaryColor())
                    .textColor(MaterialTheme.getInstance().getOnPrimaryColor())
            );
        });
    }
}
```

## Vantagens

1. **Sem dependência de CSS**: Todos os estilos são definidos em Java
2. **Consistência**: Tema centralizado garante aparência uniforme
3. **Flexibilidade**: Fácil personalização e modificação
4. **Manutenibilidade**: Código organizado e fácil de manter
5. **Material Design**: Segue as diretrizes do Material Design
6. **Tipografia**: Sistema tipográfico hierárquico bem definido
7. **Cores**: Paleta de cores semântica e acessível

## Personalização

Para personalizar o tema, você pode:

1. **Modificar cores**: Use os métodos `setPrimaryColor()`, `setSecondaryColor()`, etc.
2. **Alterar fontes**: Use `setFontFamily()` para mudar a família de fontes
3. **Ajustar espaçamentos**: Modifique `spacingUnit`, `borderRadius`, etc.
4. **Criar temas customizados**: Estenda `MaterialTheme` para temas específicos

Este sistema oferece uma solução robusta e flexível para estilização de aplicações JavaFX sem a necessidade de arquivos CSS externos.
