// src/services/authService.ts
import AsyncStorage from "@react-native-async-storage/async-storage";

export async function login(email: string, senha: string) {
  const fakeEmail = "teste@repapp.com";
  const fakePassword = "123456";

  return new Promise(async (resolve, reject) => {
    setTimeout(async () => {
      if (email === fakeEmail && senha === fakePassword) {
        const fakeToken = "token_fake_123";

        await AsyncStorage.setItem("token", fakeToken);

        resolve({
          token: fakeToken,
          user: {
            nome: "Usuário Teste",
            email: fakeEmail,
          },
        });
      } else {
        reject({ message: "Email ou senha incorretos" });
      }
    }, 800); 
  });
}

export async function logout() {
  await AsyncStorage.removeItem("token");
}

export async function register(
  nome: string,
  email: string,
  senha: string,
  telefone: string,
  universidade: string,
  anoIngresso: string
) {
  return {
    success: true,
    message: "Usuário registrado (mock)",
    user: {
      nome,
      email,
    },
  };
}
