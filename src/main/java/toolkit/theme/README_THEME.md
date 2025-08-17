# Sistema de Tema Global - Documentação

Este documento explica como usar o sistema de tema global implementado para componentes JavaFX personalizados.

## Visão Geral

O sistema de tema permite definir estilos programaticamente sem depender de arquivos CSS externos. Ele inclui:

- **MaterialTheme**: Classe singleton que define todas as cores, tipografia e espaçamentos
- **ThemeStyler**: Utilitário para aplicar estilos aos componentes
- **FontLoader**: Carregador de fontes personalizadas dos recursos

## Componentes Principais

### 1. MaterialTheme

Classe singleton que centraliza todas as configurações de tema:

```java
MaterialTheme theme = MaterialTheme.getInstance();

// Personalizar cores
theme.setPrimaryColor(Color.web("#FF5722"))
     .setSecondaryColor(Color.web("#4CAF50"))
     .setBackgroundColor(Color.web("#FAFAFA"));

// Personalizar família de fontes
theme.setFontFamily("Montserrat");
```

### 2. ThemeStyler

Utilitário com métodos estáticos para aplicar estilos:

```java
// Estilos tipográficos
ThemeStyler.h1(node);      // Aplica estilo H1
ThemeStyler.h2(node);      // Aplica estilo H2
ThemeStyler.body(node);    // Aplica estilo corpo
ThemeStyler.caption(node); // Aplica estilo legenda

// Cores
ThemeStyler.primary(node);     // Cor primária
ThemeStyler.secondary(node);   // Cor secundária
ThemeStyler.error(node);       // Cor de erro
ThemeStyler.onBackground(node); // Cor de texto sobre fundo

// Estilos específicos
ThemeStyler.textField(node);   // Estilo de campo de texto
ThemeStyler.buttonStyle(node); // Estilo de botão
ThemeStyler.card(node);        // Estilo de card
ThemeStyler.container(node);   // Estilo de container
```

### 3. FontLoader

Carregador de fontes personalizadas dos recursos:

```java
// Carrega todas as fontes disponíveis
FontLoader.loadFonts();

// Obtém fonte específica
Font montserratBold = FontLoader.getFont("Montserrat-Bold");
Font poppinsRegular = FontLoader.getFont("Poppins-Regular");

// Obtém fonte com tamanho personalizado
Font largeFont = FontLoader.getFontWithSize("Montserrat-Bold", 24);

// Obtém fonte com peso e tamanho
Font customFont = FontLoader.getFontWithWeight("Poppins-SemiBold", FontWeight.SEMI_BOLD, 18);

// Lista fontes carregadas
FontLoader.listLoadedFonts();
```

## Como Usar

### 1. Aplicando Estilos Tipográficos

```java
// Usando componentes declarativos com tema
Label_ title = new Label_("Meu Título", modifier ->
    modifier.h1().styles().primary()
);

Text_ body = new Text_("Texto do corpo", modifier ->
    modifier.body().styles().onBackground()
);

TextField_ input = new TextField_("Digite aqui...", modifier ->
    modifier.applyTheme().marginTop(20)
);
```

### 2. Aplicando Cores do Tema

```java
// Cores primárias e secundárias
Label_ primaryText = new Label_("Texto Primário", modifier ->
    modifier.styles().primary()
);

Label_ secondaryText = new Label_("Texto Secundário", modifier ->
    modifier.styles().secondary()
);

// Cores de estado
Label_ errorText = new Label_("Erro", modifier ->
    modifier.styles().error()
);

Label_ disabledText = new Label_("Desabilitado", modifier ->
    modifier.styles().disabled()
);
```

### 3. Usando Fontes Personalizadas

```java
// Carrega todas as fontes disponíveis
MaterialTheme theme = MaterialTheme.getInstance();
theme.loadAllFonts();

// Aplica fonte específica diretamente
Label_ customTitle = new Label_("Título Personalizado");
customTitle.setFont(theme.getCustomFont("Montserrat-Bold", 24));

// Ou usando o FontLoader diretamente
import toolkit.theme.FontLoader;

Label_ poppinsTitle = new Label_("Título em Poppins");
poppinsTitle.setFont(FontLoader.getFontWithWeight("Poppins-SemiBold", FontWeight.SEMI_BOLD, 20));

// Lista fontes disponíveis
FontLoader.listLoadedFonts();
```

### 4. Exemplo Completo

```java
public class MyApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Inicializa e personaliza o tema
        MaterialTheme theme = MaterialTheme.getInstance();
        theme.setPrimaryColor(Color.web("#FF5722"))
             .setFontFamily("Montserrat");

        // Carrega fontes personalizadas
        theme.loadAllFonts();

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));

        // Aplica estilo de container
        ThemeStyler.container(root);

        // Cria componentes com tema
        Label_ title = new Label_("Minha Aplicação", modifier ->
            modifier.h1().styles().primary()
        );

        Text_ subtitle = new Text_("Subtítulo em fonte personalizada", modifier ->
            modifier.h2().styles().onBackground()
        );

        // Aplica fonte personalizada de forma elegante
        subtitle.font(theme.getCustomFont("Montserrat-Bold", 20));

        TextField_ input = new TextField_("Digite aqui...", modifier ->
            modifier.applyTheme().marginTop(20)
        );

        root.getChildren().addAll(title, subtitle, input);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("App com Tema");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
```

## Fontes Disponíveis

O sistema carrega automaticamente as seguintes fontes:

### Montserrat

- Montserrat-Regular
- Montserrat-Bold
- Montserrat-Medium
- Montserrat-Light
- Montserrat-SemiBold
- Montserrat-Black

### Poppins

- Poppins-Regular
- Poppins-Bold
- Poppins-Medium
- Poppins-Light
- Poppins-SemiBold
- Poppins-Black

## Cores Disponíveis

- **Primárias**: `primaryColor`, `primaryVariantColor`
- **Secundárias**: `secondaryColor`, `secondaryVariantColor`
- **Fundo**: `backgroundColor`, `surfaceColor`
- **Texto**: `onPrimaryColor`, `onSecondaryColor`, `onBackgroundColor`, `onSurfaceColor`
- **Estado**: `errorColor`, `disabledColor`, `focusColor`, `hoverColor`

## Tipografia

- **Cabeçalhos**: H1 (32px), H2 (24px), H3 (20px), H4 (18px), H5 (16px), H6 (14px)
- **Corpo**: 14px
- **Legenda**: 12px
- **Botão**: 14px

## Espaçamentos

- **Unidade base**: 8px
- **Métodos**: `theme.spacing(1)` = 8px, `theme.spacing(2)` = 16px
- **Border radius**: 4px
- **Border width**: 1px

## Método font() Elegante

Todos os componentes declarativos (`Text_`, `Label_`, `Button_`, `TextField_`) agora possuem o método `font()` no `InnerModifier`, permitindo definir fontes de forma mais elegante:

```java
// Antes (menos elegante)
Label_ title = new Label_("Título");
title.setFont(theme.getCustomFont("Montserrat-Bold", 24));

// Agora (mais elegante)
Label_ title = new Label_("Título", modifier ->
    modifier.font(theme.getCustomFont("Montserrat-Bold", 24))
           .styles().primary()
);
```

## Vantagens

1. **Sem dependência de CSS**: Todos os estilos são aplicados programaticamente
2. **Consistência**: Sistema centralizado de design tokens
3. **Flexibilidade**: Fácil personalização e mudança de temas
4. **Performance**: Sem overhead de parsing CSS
5. **Type-safe**: Compilação verifica a existência dos métodos
6. **Fontes personalizadas**: Suporte nativo para fontes TTF/OTF
7. **API elegante**: Método `font()` integrado ao sistema de modificadores

## Exemplos de Uso

Veja os arquivos de exemplo:

- `ThemeExample.java` - Exemplo básico do sistema de tema
- `FontExample.java` - Exemplo de uso de fontes personalizadas
- `FontMethodExample.java` - Exemplo do método `font()` elegante
