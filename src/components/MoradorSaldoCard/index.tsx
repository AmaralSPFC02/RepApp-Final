import React from "react";
import { View, Text, Image } from "react-native";
import { styles } from "./styles";

export default function MoradorSaldoCard({ nome, valor, tipo, foto }) {
  const positivo = valor > 0;

  return (
    <View style={styles.container}>
      <Image source={{ uri: foto }} style={styles.avatar} />

      <View style={styles.info}>
        <Text style={styles.nome}>{nome}</Text>
        <Text style={styles.sub}>
          {positivo ? "Deve receber" : "Deve pagar"}
        </Text>
      </View>

      <Text
        style={[
          styles.valor,
          { color: positivo ? "#1DB954" : "#E53935" },
        ]}
      >
        {positivo ? `+R$${valor},00` : `-R$${Math.abs(valor)},00`}
      </Text>
    </View>
  );
}
