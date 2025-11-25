import React, { useState } from "react";
import {
  View,
  Text,
  ScrollView,
  TouchableOpacity,
  StyleSheet,
  Share,
} from "react-native";
import { MaterialIcons, Feather } from "@expo/vector-icons";


export default function Mural() {
  const [selectedFilter, setSelectedFilter] = useState("Todos");

  const filters = ["Todos", "Importantes", "Financeiro", "Geral", "Eventos", "Manutenção"];

  const avisos = [
    {
      id: 1,
      title: "Vencimento do Aluguel",
      text: "O aluguel de dezembro vence no dia 10/12. Por favor, façam o pagamento até esta data para evitar multas.",
      category: "URGENTE",
      time: "Há 2 dias",
      type: "warning",
    },
    {
      id: 2,
      title: "Conta de Luz - Novembro",
      text: "A conta de luz chegou no valor de R$ 180,00. Dividindo por 4 pessoas, fica R$ 45,00 para cada um. Prazo para pagamento: 15/12.",
      category: "FINANCEIRO",
      time: "Há 1 dia",
      type: "finance",
    },
    {
      id: 3,
      title: "Limpeza da Cozinha",
      text: "Pessoal, vamos manter a cozinha sempre limpa após o uso. Lembrem-se de lavar a louça e limpar o fogão depois de cozinhar.",
      category: "GERAL",
      time: "Há 3 dias",
      type: "geral",
    },
    {
      id: 4,
      title: "Festa de Fim de Ano",
      text: "Que tal organizarmos uma festa de fim de ano aqui na rep? Estava pensando no dia 28/12. Quem topa? Vamos conversar sobre os detalhes!",
      category: "EVENTO",
      time: "Há 5 dias",
      type: "evento",
    },
    {
      id: 5,
      title: "Manutenção do Chuveiro",
      text: "O chuveiro do banheiro 2 está com problema no aquecimento. O técnico virá na quinta-feira às 14h.",
      category: "MANUTENÇÃO",
      time: "Há 1 semana",
      type: "manutencao",
    },
  ];

  const filterAvisos = () => {
    if (selectedFilter === "Todos") return avisos;
    return avisos.filter((a) => a.category.toUpperCase() === selectedFilter.toUpperCase());
  };

  const handleShare = async (title: string, text: string) => {
    await Share.share({
      message: `${title}\n\n${text}`,
    });
  };

  return (
    <View style={styles.container}>
      {/* HEADER */}
      <View style={styles.header}>
        <TouchableOpacity>
          <Feather name="arrow-left" size={24} color="#000" />
        </TouchableOpacity>
        <Text style={styles.headerTitle}>Mural de Avisos</Text>
        <Feather name="more-vertical" size={22} color="#000" />
      </View>

      {/* FILTROS */}
      <ScrollView
        horizontal
        showsHorizontalScrollIndicator={false}
        style={styles.filterScroll}
      >
        {filters.map((f) => (
          <TouchableOpacity
            key={f}
            style={[
              styles.filterButton,
              selectedFilter === f && styles.filterSelected,
            ]}
            onPress={() => setSelectedFilter(f)}
          >
            <Text
              style={[
                styles.filterText,
                selectedFilter === f && styles.filterTextSelected,
              ]}
            >
              {f}
            </Text>
          </TouchableOpacity>
        ))}
      </ScrollView>

      {/* LISTA DE AVISOS */}
      <ScrollView contentContainerStyle={{ paddingBottom: 100 }}>
        {filterAvisos().map((a) => (
          <View
            key={a.id}
            style={[
              styles.card,
              a.type === "warning" && styles.cardWarning,
            ]}
          >
            {/* Título */}
            <Text style={[styles.cardTitle, a.type === "warning" && { color: "#B30000" }]}>
              {a.title}
            </Text>

            {/* Texto */}
            <Text style={styles.cardText}>{a.text}</Text>

            {/* Rodapé */}
            <View style={styles.cardFooter}>
              <View style={styles.tag}>
                <Text style={styles.tagText}>{a.category}</Text>
              </View>

              <Text style={styles.timeText}>{a.time}</Text>

              {/* Botões de ação */}
              <View style={styles.actionRow}>
                <TouchableOpacity>
                  <MaterialIcons name="bookmark-border" size={22} color="#444" />
                </TouchableOpacity>

                <TouchableOpacity onPress={() => handleShare(a.title, a.text)}>
                  <Feather name="share-2" size={20} color="#444" />
                </TouchableOpacity>
              </View>
            </View>
          </View>
        ))}
      </ScrollView>

      {/* BOTÃO FLUTUANTE */}
      <TouchableOpacity style={styles.fab}>
        <Feather name="plus" size={28} color="#fff" />
      </TouchableOpacity>
    </View>
  );
}

import styles from "./styles";({
  container: { flex: 1, backgroundColor: "#F5F7FA" },

  /* HEADER ------------------ */
  header: {
    flexDirection: "row",
    alignItems: "center",
    padding: 16,
    justifyContent: "space-between",
    backgroundColor: "#fff",
    borderBottomWidth: 1,
    borderColor: "#DDD",
  },
  headerTitle: { fontSize: 18, fontWeight: "bold" },

  /* FILTROS ------------------ */
  filterScroll: { paddingVertical: 10, backgroundColor: "#fff" },
  filterButton: {
    paddingVertical: 6,
    paddingHorizontal: 14,
    borderRadius: 20,
    backgroundColor: "#E5E7EB",
    marginHorizontal: 6,
  },
  filterSelected: {
    backgroundColor: "#1A73E8",
  },
  filterText: { color: "#333", fontWeight: "500" },
  filterTextSelected: { color: "#fff" },

  /* CARDS ------------------ */
  card: {
    backgroundColor: "#fff",
    marginHorizontal: 16,
    marginVertical: 8,
    padding: 16,
    borderRadius: 10,
    elevation: 2,
    shadowColor: "#0002",
  },
  cardWarning: {
    backgroundColor: "#FFE4E4",
    borderLeftWidth: 4,
    borderLeftColor: "#B30000",
  },
  cardTitle: { fontSize: 16, fontWeight: "bold", marginBottom: 6 },
  cardText: { fontSize: 14, color: "#444", marginBottom: 12 },

  cardFooter: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-between",
  },

  tag: {
    backgroundColor: "#D9EFFF",
    paddingVertical: 4,
    paddingHorizontal: 10,
    borderRadius: 6,
  },
  tagText: { fontSize: 12, fontWeight: "bold", color: "#1A73E8" },
  timeText: { fontSize: 12, color: "#777" },

  actionRow: {
    flexDirection: "row",
    gap: 16,
  },

  /* BOTÃO FLUTUANTE ----------- */
  fab: {
    position: "absolute",
    bottom: 25,
    right: 25,
    backgroundColor: "#1A73E8",
    width: 60,
    height: 60,
    borderRadius: 30,
    justifyContent: "center",
    alignItems: "center",
    elevation: 4,
  },
});
