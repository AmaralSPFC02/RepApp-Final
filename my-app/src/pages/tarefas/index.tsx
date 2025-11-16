import React, { useState } from "react";
import { View, Text, ScrollView } from "react-native";
import { SafeAreaView } from "react-native-safe-area-context";
import { MaterialIcons } from "@expo/vector-icons";
import { useNavigation } from "@react-navigation/native";

import TabsFiltro from "../../components/tarefas/TabsFiltro";
import TarefaCard from "../../components/tarefas/TarefaCard";

import styles from "./styles";

export default function Tarefas() {
  const navigation = useNavigation();
  const [filtro, setFiltro] = useState("Todas");

  const tarefas = [
    {
      titulo: "Limpar banheiro",
      descricao: "Limpeza completa do banheiro principal",
      usuario: { nome: "Joao", foto: "https://i.pravatar.cc/50?img=1" },
      status: "Pendente",
      prioridade: "Urgente",
      quando: "Hoje",
      concluida: false,
    },
    {
      titulo: "Levar lixo",
      descricao: "Colocar o lixo na calcada",
      usuario: { nome: "Maria", foto: "https://i.pravatar.cc/50?img=2" },
      status: "Concluída",
      prioridade: "Normal",
      quando: "Ontem",
      concluida: true,
    },
    {
      titulo: "Lavar louça",
      descricao: "Lavar toda louca acumulada na pia",
      usuario: { nome: "Pedro", foto: "https://i.pravatar.cc/50?img=3" },
      status: "Pendente",
      prioridade: "Normal",
      quando: "Amanhã",
      concluida: false,
    },
    {
      titulo: "Aspirar sala",
      descricao: "Aspirar o po da sala de estar",
      usuario: { nome: "Não atribuída", foto: null },
      status: "Pendente",
      prioridade: "Normal",
      quando: "Esta semana",
      concluida: false,
    },
    {
      titulo: "Organizar geladeira",
      descricao: "Limpar e organizar os itens da geladeira",
      usuario: { nome: "Ana", foto: "https://i.pravatar.cc/50?img=4" },
      status: "Pendente",
      prioridade: "Normal",
      quando: "Sexta-feira",
      concluida: false,
    },
  ];

  const tarefasFiltradas =
    filtro === "Todas"
      ? tarefas
      : tarefas.filter((t) =>
          filtro === "Pendentes"
            ? !t.concluida
            : filtro === "Concluídas"
            ? t.concluida
            : filtro === "Minhas"
            ? t.usuario.nome === "Joao" // ajustar futuramente
            : true
        );

  return (
    <SafeAreaView style={{ flex: 1, backgroundColor: "#FFF" }}>
      <View style={styles.header}>
        <MaterialIcons
          name="arrow-back"
          size={24}
          color="#000"
          onPress={() => navigation.goBack()}
        />

        <Text style={styles.title}>Tarefas</Text>

        <MaterialIcons name="add" size={26} color="#FFF" style={styles.addBtn} />
      </View>

      <TabsFiltro filtro={filtro} setFiltro={setFiltro} />

      <ScrollView
        style={{ flex: 1 }}
        contentContainerStyle={{ paddingHorizontal: 16, paddingBottom: 80 }}
        showsVerticalScrollIndicator={false}
      >
        {tarefasFiltradas.map((tarefa, index) => (
          <TarefaCard key={index} data={tarefa} />
        ))}
      </ScrollView>
    </SafeAreaView>
  );
}
