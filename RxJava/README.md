# RxJava

Snippets de infraestrutura para projetos Android baseados em RxJava 3, LiveData,
Fragments e View Binding.

## Estrutura

```text
RxJava/
├── core/
│   ├── BaseViewModel.kt
│   ├── BaseFragment.kt
│   ├── AsyncResult.kt
│   ├── ErrorResponse.kt
│   └── SingleLiveEvent.kt
├── extensions/
│   ├── ObservableExtensions.kt
│   └── ThrowableExtensions.kt
├── examples/
└── tests/
```

## Dependências

Use as versões adotadas pelo projeto de destino:

```kotlin
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:<version>")
implementation("androidx.lifecycle:lifecycle-livedata-ktx:<version>")
implementation("androidx.fragment:fragment-ktx:<version>")
implementation("io.reactivex.rxjava3:rxjava:<version>")
implementation("io.reactivex.rxjava3:rxandroid:<version>")
testImplementation("junit:junit:4.13.2")
```

Ative View Binding no módulo Android:

```kotlin
android {
    buildFeatures {
        viewBinding = true
    }
}
```

## Fluxo

```text
Repository
   -> Observable<T>
   -> toAsyncResult()
   -> Loading | Success<T> | Failure
   -> BaseViewModel
   -> LiveData
   -> BaseFragment
```

## Uso mínimo

```kotlin
fun loadUsers() {
    subscribe(
        observable = repository.getUsers(),
        onSuccess = { users.value = it },
        onError = { error.value = it },
    )
}
```

## Decisões importantes

- O `BaseViewModel` trata tanto `AsyncResult.Failure` quanto o canal `onError`.
- Não há `!!`; a nulabilidade é determinada pelo tipo genérico recebido.
- Os schedulers ficam em extensões e podem ser substituídos em testes.
- O binding do Fragment existe somente durante o ciclo de vida da View.
- `SingleLiveEvent` foi mantido por ser comum em bases com LiveData, mas possui a
  limitação de notificar apenas um observador. Em código moderno, considere
  `SharedFlow` para eventos.
- Um booleano global de loading é adequado somente para exemplos simples. Em uma
  tela com operações simultâneas, modele o loading no estado de cada operação.

## Adaptação rápida

1. Copie `core` e `extensions`.
2. Troque `com.alexbralves.android.rxjava` pelo package do projeto.
3. Remova classes que não forem necessárias.
4. Adapte `ThrowableExtensions` ao modelo de erro da API.
5. Use os arquivos de `examples` somente como referência.

Este diretório é uma referência de código, não um módulo independente para build.
