# Refatoração com Classes Abstratas - Promovendo Reutilização de Código

## Visão Geral

Esta refatoração introduz uma hierarquia de classes abstratas para promover a reutilização de código entre componentes de texto declarativos (`Label_`, `Text_`, `ClickableText`, `TextField_`).

## Problema Identificado

Antes da refatoração, cada componente tinha suas próprias implementações de:

- `InnerModifier` com métodos duplicados
- `InnerStyles` com métodos duplicados
- Lógica repetida para estilização de texto

## Solução Implementada

### 1. Classe Abstrata `TextModifier<T extends Node>`

**Métodos comuns implementados:**

- `marginTop(double margin)` - Define margem superior
- `fontSize(double fontSize)` - Define tamanho da fonte (abstrato)
- `font(Font font)` - Define fonte diretamente (abstrato)
- `h1()`, `h2()`, `h3()` - Estilos de cabeçalho
- `body()`, `caption()` - Estilos de texto

**Método abstrato:**

- `styles()` - Retorna o modificador de estilos

### 2. Classe Abstrata `TextStyles<T extends Node>`

**Métodos comuns implementados:**

- `primary()`, `secondary()`, `error()` - Cores do tema
- `onBackground()`, `onSurface()`, `disabled()` - Estados do tema
- `fontWeightBold()`, `fontWeightNormal()` - Pesos de fonte predefinidos
- `end()` - Retorna ao modificador para continuar a cadeia

**Métodos abstratos:**

- `color(Color color)` - Define cor do texto
- `fontWeight(String weight)` - Define peso da fonte

## Benefícios da Refatoração

### ✅ **Reutilização de Código**

- Métodos comuns implementados uma única vez
- Redução de duplicação entre componentes

### ✅ **Manutenibilidade**

- Mudanças em métodos comuns afetam todos os componentes
- Código mais fácil de manter e atualizar

### ✅ **Consistência**

- Comportamento uniforme entre componentes
- API consistente para estilização

### ✅ **Extensibilidade**

- Fácil adicionar novos componentes de texto
- Novos métodos podem ser implementados na classe base

## Estrutura das Classes

```
TextModifier<T> (abstrata)
├── Label_.InnerModifier
├── Text_.InnerModifier
├── ClickableText.InnerModifier
└── TextField_.InnerModifier

TextStyles<T> (abstrata)
├── Label_.InnerStyles
├── Text_.InnerStyles
├── ClickableText.InnerStyles
└── TextField_.InnerStyles
```

## Exemplo de Uso

```java
// Antes: Cada componente tinha implementação própria
Label_ label = new Label_("Texto", modifier ->
    modifier.fontSize(16).styles().color(Color.RED));

Text_ text = new Text_("Texto", modifier ->
    modifier.fontSize(16).styles().color(Color.RED));

// Agora: Métodos herdados da classe abstrata
Label_ label = new Label_("Texto", modifier ->
    modifier.fontSize(16).styles().color(Color.RED));

Text_ text = new Text_("Texto", modifier ->
    modifier.fontSize(16).styles().color(Color.RED));
```

## Métodos Específicos Mantidos

Cada componente mantém seus métodos específicos:

- **Label\_**: `alignment(TextAlignment)`
- **Text\_**: `maxWidth(double)`, `alignment(TextAlignment)`
- **ClickableText**: `onClick(Runnable)`, `maxWidth(double)`
- **TextField\_**: `applyTheme()`

## Como Adicionar Novos Componentes

1. **Estender `TextModifier<T>`** para o `InnerModifier`
2. **Implementar métodos abstratos** (`fontSize`, `font`, `styles`)
3. **Estender `TextStyles<T>`** para o `InnerStyles`
4. **Implementar métodos abstratos** (`color`, `fontWeight`)
5. **Adicionar métodos específicos** do componente

## Exemplo de Implementação

```java
public class NewTextComponent extends SomeJavaFXNode {
    public static class InnerModifier extends TextModifier<NewTextComponent> {
        public InnerModifier(NewTextComponent node) {
            super(node);
        }

        @Override
        public InnerModifier fontSize(double fontSize) {
            // Implementação específica
            return this;
        }

        @Override
        public InnerModifier font(Font font) {
            // Implementação específica
            return this;
        }

        @Override
        public InnerStyles styles() {
            return new InnerStyles(this);
        }
    }

    public static class InnerStyles extends TextStyles<NewTextComponent> {
        // Implementação dos métodos abstratos
    }
}
```

## Arquivos Modificados

- `TextModifier.java` - Nova classe abstrata
- `TextStyles.java` - Nova classe abstrata
- `Label_.java` - Refatorado para usar classes abstratas
- `Text_.java` - Refatorado para usar classes abstratas
- `ClickableText.java` - Refatorado para usar classes abstratas
- `TextField_.java` - Refatorado para usar classes abstratas

## Exemplo de Demonstração

Execute `RefactoringExample.java` para ver a refatoração em ação, demonstrando como os métodos herdados funcionam em todos os componentes.

## Próximos Passos

- Considerar refatoração para outros tipos de componentes (botões, campos, etc.)
- Implementar testes unitários para as classes abstratas
- Adicionar validações e tratamento de erros nas classes base
