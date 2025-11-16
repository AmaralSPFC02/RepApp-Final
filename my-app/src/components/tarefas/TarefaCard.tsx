import React from "react";
import { View, Text, Image } from "react-native";
import { MaterialIcons } from "@expo/vector-icons";

export default function TarefaCard({ data }) {
  return (
    <View
      style={{
        backgroundColor: "#FFF",
        padding: 16,
        borderRadius: 16,
        marginBottom: 12,
        borderWidth: 1,
        borderColor: "#EEE",
      }}
    >
      <View style={{ flexDirection: "row", alignItems: "center", marginBottom: 4 }}>
        {data.concluida ? (
          <MaterialIcons
            name="check-circle"
            size={22}
            color="#4CAF50"
            style={{ marginRight: 8 }}
          />
        ) : (
          <MaterialIcons
            name="radio-button-unchecked"
            size={22}
            color="#CCC"
            style={{ marginRight: 8 }}
          />
        )}

        <Text style={{ fontSize: 16, fontWeight: "600" }}>{data.titulo}</Text>
      </View>

      <Text style={{ color: "#777", marginBottom: 12 }}>{data.descricao}</Text>

      <View style={{ flexDirection: "row", alignItems: "center" }}>
        {data.usuario.foto ? (
          <Image
            source={{ uri: data.usuario.foto }}
            style={{
              width: 28,
              height: 28,
              borderRadius: 14,
              marginRight: 6,
            }}
          />
        ) : (
          <MaterialIcons
            name="person-outline"
            size={26}
            color="#999"
            style={{ marginRight: 6 }}
          />
        )}

        <Text style={{ color: "#444", marginRight: 12 }}>{data.usuario.nome}</Text>

        <View
          style={{
            backgroundColor:
              data.prioridade === "Urgente" ? "#FFD400" : "#E5E5FF",
            paddingHorizontal: 8,
            paddingVertical: 4,
            borderRadius: 8,
            marginRight: 12,
          }}
        >
          <Text style={{ fontSize: 12, fontWeight: "600", color: "#444" }}>
            {data.prioridade}
          </Text>
        </View>

        <Text style={{ color: "#888", marginLeft: "auto" }}>{data.quando}</Text>
      </View>
    </View>
  );
}
