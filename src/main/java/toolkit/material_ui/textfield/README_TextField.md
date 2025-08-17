# TextField\_ - Material-UI para JavaFX

Este componente implementa um `TextField` seguindo o padrão do Material-UI React, com suporte a labels flutuantes, variantes e funcionalidade multiline.

## Funcionalidades

- **Label Flutuante**: O label se move para cima da borda quando o campo tem foco ou conteúdo
- **Variantes**: `outlined`, `filled`, `standard`
- **Multiline**: Transforma o campo em uma `TextArea` quando ativado
- **Placeholder**: Texto de exemplo dentro do campo
- **Estados de Foco**: Mudanças visuais automáticas (cor da borda, espessura)
- **Estilização**: Cores, bordas e estilos personalizáveis

## Comportamento Material-UI

### Label Flutuante e Placeholder

O componente agora implementa o comportamento correto do Material-UI quando há tanto label quanto placeholder:

- **Sem Foco e Sem Conteúdo**: Mostra apenas a label dentro do campo (como placeholder)
- **Com Foco e Sem Conteúdo**: Label sobe para posição flutuante e o placeholder aparece
- **Com Conteúdo**: Label permanece flutuante independente do foco

### Estados Visuais

- **Estado Inicial**: Label dentro do campo (cinza), placeholder oculto
- **Com Foco**: Label flutuante acima da borda (azul, negrito), placeholder visível
- **Com Conteúdo**: Label flutuante acima da borda, conteúdo visível

### Estados de Foco

- **Sem Foco**: Borda cinza
- **Com Foco**: Borda azul (#1976d2) e mais espessa
- **Com Conteúdo**: Borda cinza escuro para indicar preenchimento

## Uso Básico

### Construtor Simples

```java
TextField_ field = new TextField_();
```

### Com Valor Inicial

```java
TextField_ field = new TextField_("Texto inicial");
```

### Com Variante

```java
TextField_ field = new TextField_("", "filled");
```

### Com Label Flutuante

```java
TextField_ field = new TextField_("", "outlined", false, "Nome do usuário");
```

### Com Label e Placeholder

```java
TextField_ field = new TextField_("", "outlined", false, "Nome completo");
field.modifier().placeholder("Digite seu nome completo aqui...");
```

### Multiline (TextArea)

```java
TextField_ field = new TextField_("", "outlined", true, "Descrição");
```

## Variantes

### Outlined (Padrão)

```java
TextField_ field = new TextField_("", "outlined", false, "Outlined");
```

- Borda ao redor do campo
- Fundo branco
- Cantos arredondados
- Label flutuante sobre a borda

### Filled

```java
TextField_ field = new TextField_("", "filled", false, "Filled");
```

- Fundo cinza claro
- Sem borda visível
- Label flutuante sobre o fundo
- Padding maior no topo

### Standard

```java
TextField_ field = new TextField_("", "standard", false, "Standard");
```

- Sem fundo
- Borda apenas na parte inferior
- Estilo minimalista
- Label flutuante sobre a linha

## Modificadores

### Label

```java
field.modifier().label("Novo label");
```

### Variante

```java
field.modifier().variant("filled");
```

### Multiline

```java
field.modifier().multiline(true);
```

### Placeholder

```java
field.modifier().placeholder("Digite aqui...");
```

### Estilos

```java
field.modifier()
    .fontSize(16)
    .font(Font.font("Arial"))
    .styles()
        .color(Color.BLACK)
        .fontWeight("bold")
        .focusColor("#1976d2")
        .borderRadius(8)
        .borderWidth(2);
```

## Exemplo Completo

```java
TextField_ userField = new TextField_("", "outlined", false, "Nome do usuário");
userField.modifier()
    .placeholder("Digite seu nome completo")
    .fontSize(14)
    .styles()
        .focusColor("#1976d2")
        .borderRadius(4);
```

**Comportamento Resultante:**

- **Estado inicial**: Mostra apenas "Nome do usuário" dentro do campo
- **Com foco**: "Nome do usuário" sobe (flutuante) e "Digite seu nome completo" aparece
- **Com conteúdo**: Label permanece flutuante e o texto digitado é exibido

## Estados Visuais

### Estado Inicial

- Label dentro do campo (cinza)
- Borda cinza clara
- Placeholder visível

### Estado com Foco

- Label flutuante acima da borda (azul, negrito)
- Borda azul e mais espessa
- Placeholder oculto se houver conteúdo

### Estado com Conteúdo

- Label flutuante acima da borda
- Borda cinza escura
- Conteúdo visível

## Propriedades

### Getters

- `getLabel()`: Retorna o texto do label
- `getVariant()`: Retorna a variante atual
- `isMultiline()`: Retorna se é multiline
- `getText()`: Retorna o texto do campo
- `getPromptText()`: Retorna o placeholder

### Setters

- `setLabel(String)`: Define o texto do label
- `setVariant(String)`: Define a variante
- `setMultiline(boolean)`: Ativa/desativa multiline
- `setText(String)`: Define o texto do campo
- `setPromptText(String)`: Define o placeholder

## Compatibilidade

O componente mantém compatibilidade com o padrão Material-UI React:

- Mesmas variantes (`outlined`, `filled`, `standard`)
- Mesmo comportamento de label flutuante
- Mesmos estados de foco
- Suporte a multiline como no React
- Estilização similar

## Dependências

- JavaFX
- Toolkit de componentes declarativos
- Sistema de temas

## Notas Técnicas

- Usa `StackPane` para sobreposição do label e campo
- Listeners automáticos para foco e conteúdo
- Atualização automática de estilos baseada no estado
- Transições suaves entre estados
- **Lógica de Exibição**: O método `updateLabelPosition()` controla dinamicamente quando mostrar a label ou o placeholder:
  - Sem foco e sem conteúdo: Label visível, placeholder oculto
  - Com foco e sem conteúdo: Label flutuante, placeholder visível
  - Com conteúdo: Label sempre flutuante
- **Gerenciamento de Placeholder**: O placeholder é automaticamente mostrado/ocultado baseado no estado do campo
