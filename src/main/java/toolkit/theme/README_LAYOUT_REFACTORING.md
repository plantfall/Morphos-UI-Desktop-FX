# Refatoração dos Componentes de Layout

## Problema Identificado

Os componentes de layout `Row_` e `Column` tinham muitos métodos duplicados em suas classes `InnerModifier` e `InnerStyles`, resultando em:

- Duplicação de código
- Dificuldade de manutenção
- Inconsistências entre implementações
- Falta de padronização na API

## Solução Implementada

### 1. Classe Abstrata `LayoutModifier<T>`

Centraliza os métodos comuns de layout:

**Métodos Implementados:**

- `alignment(Pos alignment)` - Alinhamento do container
- `spacing(double spacing)` - Espaçamento entre elementos filhos
- `padding(double all)` - Padding uniforme
- `padding(double top, double right, double bottom, double left)` - Padding específico
- `marginTop(double margin)` - Margem superior
- `fillMaxHeight(boolean enable)` - Preencher altura máxima
- `fillMaxWidth(boolean enable)` - Preencher largura máxima
- `maxHeight(double maxHeight)` - Altura máxima
- `maxWidth(double maxWidth)` - Largura máxima
- `height(double height)` - Altura fixa
- `width(double width)` - Largura fixa
- `styles()` - Retorna o container de estilos

### 2. Classe Abstrata `LayoutStyles<T>`

Centraliza os métodos comuns de estilos:

**Métodos Implementados:**

- `bgColor(Color color)` - Cor de fundo
- `borderRadius(int radiusAll)` - Raio dos cantos
- `borderColor(Paint color)` - Cor da borda
- `end()` - Retorna ao modificador para encadeamento

## Benefícios da Refatoração

### ✅ Reutilização de Código

- Métodos comuns implementados uma única vez
- Consistência entre componentes de layout
- Facilita manutenção e atualizações

### ✅ API Padronizada

- Interface uniforme para todos os componentes de layout
- Comportamento previsível entre `Row_` e `Column`
- Facilita aprendizado e uso

### ✅ Extensibilidade

- Fácil adicionar novos componentes de layout
- Implementação de novos métodos comuns centralizada
- Herança clara e organizada

## Estrutura das Classes

```
LayoutModifier<T> (abstrata)
├── Row_.InnerModifier extends LayoutModifier<Row_>
└── Column.InnerModifier extends LayoutModifier<Column>

LayoutStyles<T> (abstrata)
├── Row_.InnerStyles extends LayoutStyles<Row_>
└── Column.InnerStyles extends LayoutStyles<Column>
```

## Exemplo de Uso

```java
// Row_ usando métodos herdados
Row_ headerRow = new Row_(
    modifier -> modifier
        .alignment(Pos.CENTER)        // Herdado de LayoutModifier
        .spacing(20)                 // Herdado de LayoutModifier
        .padding(20)                 // Herdado de LayoutModifier
        .fillMaxWidth(true)          // Herdado de LayoutModifier
        .styles().bgColor(Color.LIGHTBLUE) // Herdado de LayoutStyles
);

// Column usando métodos herdados
Column contentColumn = new Column(
    modifier -> modifier
        .alignment(Pos.TOP_CENTER)   // Herdado de LayoutModifier
        .spacing(15)                 // Herdado de LayoutModifier
        .padding(30)                 // Herdado de LayoutModifier
        .fillMaxWidth(true)          // Herdado de LayoutModifier
        .styles().bgColor(Color.WHITE).borderRadius(10) // Herdados de LayoutStyles
);
```

## Métodos Específicos Mantidos

### Row\_

- `spaceBetween()` - Distribui elementos com espaço entre eles

### Column

- `borderColor(Paint color)` - Implementação específica com gerenciamento de estado

## Como Adicionar Novos Componentes de Layout

1. **Estender `LayoutModifier<T>`:**

   ```java
   public static class InnerModifier extends LayoutModifier<NovoComponente> {
       public InnerModifier(NovoComponente node) {
           super(node);
       }

       // Implementar métodos abstratos
       @Override
       public InnerModifier alignment(Pos alignment) {
           node.setAlignment(alignment);
           return this;
       }
       // ... outros métodos
   }
   ```

2. **Estender `LayoutStyles<T>`:**

   ```java
   public static class InnerStyles extends LayoutStyles<NovoComponente> {
       public InnerStyles(InnerModifier modifier) {
           super(modifier);
       }

       // Implementar métodos abstratos
       @Override
       public InnerStyles bgColor(Color color) {
           modifier.getNode().setBackground(...);
           return this;
       }
       // ... outros métodos
   }
   ```

## Arquivos Modificados

- `src/main/java/toolkit/declarative_components/modifiers/LayoutModifier.java` (novo)
- `src/main/java/toolkit/declarative_components/modifiers/LayoutStyles.java` (novo)
- `src/main/java/toolkit/declarative_components/Row_.java`
- `src/main/java/toolkit/declarative_components/Column.java`
- `src/main/java/toolkit/theme/LayoutRefactoringExample.java` (novo)

## Resultado Final

A refatoração resultou em:

- **Redução significativa de código duplicado**
- **API mais consistente e previsível**
- **Facilidade para adicionar novos componentes de layout**
- **Manutenção simplificada**
- **Melhor organização do código**

Os componentes `Row_` e `Column` agora compartilham uma base comum sólida, mantendo suas funcionalidades específicas enquanto promovem reutilização de código e consistência na API.
