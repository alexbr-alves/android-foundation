# Compose

Snippets para telas Android com Jetpack Compose, fluxo unidirecional de dados e
separação entre estado persistente, ações do usuário e efeitos pontuais.

```text
Compose/
├── core/
│   ├── BaseViewModel.kt
│   ├── UiState.kt
│   ├── UiEvent.kt
│   └── UiEffect.kt
├── components/
│   └── StateContent.kt
├── extensions/
│   └── FlowExtensions.kt
├── examples/
└── tests/
```

## Dependências

```kotlin
implementation(platform("androidx.compose:compose-bom:<version>"))
implementation("androidx.compose.material3:material3")
implementation("androidx.lifecycle:lifecycle-runtime-compose:<version>")
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:<version>")
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:<version>")
testImplementation("junit:junit:4.13.2")
```

## Fluxo

```text
UI --UiEvent--> ViewModel --StateFlow--> UI
                      |
                      +--UiEffect------> navegação/snackbar
```

## Decisões

- `StateFlow` mantém o estado renderizável da tela.
- `Channel` exposto como `Flow` entrega efeitos pontuais a um consumidor.
- `collectAsStateWithLifecycle` respeita o ciclo de vida Android.
- A função `UsersScreen` recebe somente estado e callbacks, facilitando testes e previews.
- `UsersRoute` conecta a tela à ViewModel e trata efeitos externos.
- Cancelamentos de coroutine são propagados, não convertidos em erro.

Copie apenas os arquivos necessários, ajuste os packages e adapte os contratos.
Este diretório é uma referência de código, não um módulo para build.

