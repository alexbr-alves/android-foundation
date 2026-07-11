# Coroutines

Snippets Android modernos baseados em Kotlin Coroutines, StateFlow, SharedFlow,
ViewModel, Fragment e View Binding.

```text
Coroutines/
├── core/
│   ├── BaseViewModel.kt
│   ├── BaseFragment.kt
│   ├── AsyncResult.kt
│   └── ErrorResponse.kt
├── extensions/
│   ├── FlowExtensions.kt
│   └── ThrowableExtensions.kt
├── examples/
└── tests/
```

## Dependências

```kotlin
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:<version>")
implementation("androidx.lifecycle:lifecycle-runtime-ktx:<version>")
implementation("androidx.fragment:fragment-ktx:<version>")
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:<version>")
testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:<version>")
testImplementation("junit:junit:4.13.2")
```

## Conceitos demonstrados

- `viewModelScope` cancela operações quando a ViewModel é finalizada.
- `StateFlow` representa estado persistente e sempre possui um valor atual.
- `SharedFlow` representa eventos pontuais, como mensagens de erro.
- `repeatOnLifecycle` coleta flows apenas enquanto a View está iniciada.
- O dispatcher de IO pode ser injetado para testes.
- O binding é limpo em `onDestroyView`.

## Uso rápido

```kotlin
fun loadUsers() {
    execute(
        operation = repository::getUsers,
        onSuccess = { users.value = it },
    )
}
```

Copie os arquivos necessários, ajuste os packages e adapte o modelo de erro.
Este diretório é uma referência de código, não um módulo para build.
