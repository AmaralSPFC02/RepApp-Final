import React from "react";
import { View, Text } from "react-native";
import { Feather } from "@expo/vector-icons";
import { styles } from "./styles";

export default function DespesaCard({
  titulo,
  valor,
  pagoPor,
  data,
  porPessoa,
  arquivo,
  divisao,
}) {
  return (
    <View style={styles.card}>
      <View style={styles.row}>
        <Text style={styles.title}>{titulo}</Text>
        <Text style={styles.value}>R${valor}</Text>
      </View>

      <Text style={styles.sub}>Pago por {pagoPor}</Text>
      <Text style={styles.sub}>{data}</Text>
      <Text style={styles.sub}>R$ {porPessoa} por pessoa</Text>

      <View style={styles.fileRow}>
        <Feather name="file-text" size={16} color="#2D6CF6" />
        <Text style={styles.file}>{arquivo}</Text>
      </View>

      <Text style={styles.divTitle}>Diviso:</Text>

      <View style={styles.divRow}>
        {divisao.map((p, i) => (
          <View key={i} style={{ alignItems: "center" }}>
            <Feather
              name={p.status === "pago" ? "check" : "alert-circle"}
              size={18}
              color={p.status === "pago" ? "green" : "red"}
            />
            <Text style={{ fontSize: 12 }}>
              {p.status === "deve" ? `Deve R$ ${p.valor}` : "Pago"}
            </Text>
          </View>
        ))}
      </View>
    </View>
  );
}
