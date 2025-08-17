# Reutilização de Estilos - Extraindo Modifiers para Cima

## Visão Geral

Este documento explica como extrair modifiers para cima e criar estilos reutilizáveis para componentes `Label_` e outros componentes de texto, promovendo a reutilização de código e consistência visual.

## Conceito Principal

Ao invés de definir o mesmo estilo repetidamente em cada `Label_`, você pode:

1. **Extrair o modifier para uma variável**
2. **Criar funções de estilo reutilizáveis**
3. **Aplicar estilos em lote**
4. **Criar estilos condicionais e compostos**

## Exemplos Práticos

### 1. Extraindo Modifier para Variável

```java
// Criando um modifier com estilo específico
var headingModifier = new Label_("").modifier()
    .fontSize(28)
    .styles().color(Color.DARKBLUE).fontWeightBold();

// Aplicando o mesmo estilo em múltiplas Label_
Label_ heading1 = new Label_("Título Principal");
Label_ heading2 = new Label_("Título Secundário");

// Aplicando o estilo do modifier extraído
heading1.setFont(headingModifier.getNode().getFont());
heading1.setStyle(headingModifier.getNode().getStyle());
heading1.setTextFill(Color.DARKBLUE);

heading2.setFont(headingModifier.getNode().getFont());
heading2.setStyle(headingModifier.getNode().getStyle());
heading2.setTextFill(Color.DARKBLUE);
```

### 2. Funções de Estilo Reutilizáveis

```java
// Definindo estilos como funções lambda
var titleStyle = (Label_ label) -> label.modifier()
    .fontSize(24)
    .styles().color(Color.DARKBLUE).fontWeightBold();

var subtitleStyle = (Label_ label) -> label.modifier()
    .fontSize(18)
    .styles().color(Color.GRAY).fontWeightNormal();

var bodyStyle = (Label_ label) -> label.modifier()
    .fontSize(14)
    .styles().color(Color.BLACK).fontWeightNormal();

// Aplicando estilos reutilizáveis
Label_ title1 = new Label_("Título com Estilo Reutilizável");
titleStyle.accept(title1);

Label_ subtitle1 = new Label_("Subtítulo com Estilo Reutilizável");
subtitleStyle.accept(subtitle1);
```

### 3. Aplicando Estilos em Lote

```java
// Criando múltiplas Label_ com o mesmo estilo
Label_[] batchLabels = {
    new Label_("Item 1"),
    new Label_("Item 2"),
    new Label_("Item 3"),
    new Label_("Item 4"),
    new Label_("Item 5")
};

// Aplicando estilo em lote
for (Label_ label : batchLabels) {
    bodyStyle.accept(label);
}
```

### 4. Estilos Condicionais

```java
// Função que aplica estilo baseado em condição
var conditionalStyle = (Label_ label, boolean isImportant) -> {
    if (isImportant) {
        titleStyle.accept(label);
    } else {
        subtitleStyle.accept(label);
    }
};

Label_ importantLabel = new Label_("Este é importante!");
conditionalStyle.accept(importantLabel, true);

Label_ normalLabel = new Label_("Este é normal");
conditionalStyle.accept(normalLabel, false);
```

### 5. Estilos Compostos

```java
// Combinando estilos
var combinedStyle = (Label_ label) -> {
    titleStyle.accept(label);
    // Adicionando estilo adicional
    label.setStyle(label.getStyle() + "; -fx-underline: true;");
};

Label_ combinedLabel = new Label_("Título com sublinhado");
combinedStyle.accept(combinedLabel);
```

### 6. Estilos Baseados em Tema

```java
// Estilos que usam o sistema de tema
var themeTitleStyle = (Label_ label) -> label.modifier()
    .h1() // Usando método do tema
    .styles().primary(); // Usando cor primária do tema

var themeBodyStyle = (Label_ label) -> label.modifier()
    .body() // Usando método do tema
    .styles().onBackground(); // Usando cor do tema

Label_ themeTitle = new Label_("Título com Tema");
themeTitleStyle.accept(themeTitle);
```

## Vantagens da Abordagem

### ✅ **Consistência Visual**
- Todos os títulos terão exatamente o mesmo estilo
- Mudanças no estilo são aplicadas automaticamente em todos os lugares

### ✅ **Reutilização de Código**
- Não há duplicação de código de estilo
- Fácil manutenção e atualização

### ✅ **Flexibilidade**
- Estilos podem ser combinados e modificados
- Aplicação condicional baseada em lógica de negócio

### ✅ **Performance**
- Estilos são definidos uma vez e reutilizados
- Menos overhead na criação de componentes

## Padrões Recomendados

### 1. **Nomenclatura Consistente**
```java
var titleStyle = (Label_ label) -> { ... };
var subtitleStyle = (Label_ label) -> { ... };
var bodyStyle = (Label_ label) -> { ... };
var errorStyle = (Label_ label) -> { ... };
```

### 2. **Organização por Categoria**
```java
// Estilos de texto
var textStyles = {
    titleStyle,
    subtitleStyle,
    bodyStyle
};

// Estilos de status
var statusStyles = {
    errorStyle,
    successStyle,
    warningStyle
};
```

### 3. **Estilos Aninhados**
```java
var primaryTitleStyle = (Label_ label) -> {
    titleStyle.accept(label);
    label.setStyle(label.getStyle() + "; -fx-border-color: blue;");
};
```

## Casos de Uso Comuns

### **Formulários**
```java
var formLabelStyle = (Label_ label) -> label.modifier()
    .fontSize(14)
    .styles().color(Color.BLACK).fontWeightNormal();

var formTitleStyle = (Label_ label) -> label.modifier()
    .fontSize(18)
    .styles().color(Color.DARKBLUE).fontWeightBold();
```

### **Dashboards**
```java
var metricStyle = (Label_ label) -> label.modifier()
    .fontSize(32)
    .styles().color(Color.GREEN).fontWeightBold();

var metricLabelStyle = (Label_ label) -> label.modifier()
    .fontSize(12)
    .styles().color(Color.GRAY).fontWeightNormal();
```

### **Navegação**
```java
var navItemStyle = (Label_ label) -> label.modifier()
    .fontSize(16)
    .styles().color(Color.WHITE).fontWeightNormal();

var navActiveStyle = (Label_ label) -> label.modifier()
    .fontSize(16)
    .styles().color(Color.YELLOW).fontWeightBold();
```

## Arquivos de Exemplo

- `StyleReuseExample.java` - Exemplo completo de reutilização de estilos
- `LayoutRefactoringExample.java` - Exemplo atualizado com reutilização de estilos

## Conclusão

Extrair modifiers para cima e criar estilos reutilizáveis é uma prática poderosa que:

- **Reduz duplicação de código**
- **Promove consistência visual**
- **Facilita manutenção**
- **Melhora a legibilidade do código**

Esta abordagem é especialmente útil em aplicações grandes onde a consistência visual é crucial e onde você tem muitos componentes que compartilham o mesmo estilo.
