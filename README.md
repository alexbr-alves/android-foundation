# Android Base Classes

Coleção de classes-base e extensões Kotlin para consulta e reutilização rápida
em exercícios Android.

O repositório contém snippets, não um projeto compilável. Copie apenas os
arquivos necessários e ajuste os packages ao projeto de destino.

## Implementações

- [RxJava](./RxJava): ViewModel, Fragment, estados assíncronos, erros e extensões.
- [Coroutines](./Coroutines): StateFlow, SharedFlow, viewModelScope e lifecycle.

## Uso rápido

1. Copie `RxJava/core` e `RxJava/extensions` para o projeto.
2. Ajuste a declaração `package` dos arquivos.
3. Adicione as dependências listadas no README da pasta.
4. Consulte `RxJava/examples` para conectar Repository, ViewModel e Fragment.

As abstrações são deliberadamente pequenas para que sejam fáceis de explicar,
adaptar e escrever durante uma entrevista.
