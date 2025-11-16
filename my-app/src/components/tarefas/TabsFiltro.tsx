import React from "react";
import { View, Text, TouchableOpacity } from "react-native";
import { themas } from "../../global/themes";

export default function TabsFiltro({ filtro, setFiltro }) {
  const tabs = ["Todas", "Pendentes", "Concluídas", "Minhas"];

  return (
    <View
      style={{
        flexDirection: "row",
        paddingHorizontal: 12,
        marginTop: 4,
        marginBottom: 16,
      }}
    >
      {tabs.map((tab) => {
        const ativo = filtro === tab;
        return (
          <TouchableOpacity
            key={tab}
            onPress={() => setFiltro(tab)}
            style={{
              backgroundColor: ativo ? themas.colors.primary : "#F0F0F5",
              paddingVertical: 8,
              paddingHorizontal: 18,
              borderRadius: 10,
              marginRight: 8,
            }}
          >
            <Text
              style={{
                color: ativo ? "#FFF" : "#555",
                fontWeight: "600",
              }}
            >
              {tab}
            </Text>
          </TouchableOpacity>
        );
      })}
    </View>
  );
}
