// styles.ts
import { StyleSheet } from "react-native";

export default StyleSheet.create({
  container: { 
    flex: 1, 
    backgroundColor: "#F5F7FA" 
  },

  /* HEADER ------------------ */
  header: {
    flexDirection: "row",
    alignItems: "center",
    paddingTop: 22,        // título mais para baixo
    paddingBottom: 16,
    paddingHorizontal: 16,
    justifyContent: "space-between",
    backgroundColor: "#fff",
    borderBottomWidth: 1,
    borderColor: "#DDD",
  },
  headerTitle: { 
    fontSize: 18, 
    fontWeight: "bold" 
  },

  /* FILTROS ------------------ */
  filterScroll: { 
    paddingVertical: 12, 
    backgroundColor: "#fff",
  },

  filterButton: {
    minHeight: 44,              // altura ideal p/ toque
    paddingVertical: 10,
    paddingHorizontal: 20,
    borderRadius: 28,
    backgroundColor: "#E5E7EB",
    marginHorizontal: 6,
    justifyContent: "center",
    alignItems: "center",       // corrige o bug visual
  },
  filterSelected: {
    backgroundColor: "#1A73E8",
  },
  filterText: { 
    fontSize: 15,
    color: "#333", 
    fontWeight: "600",
  },
  filterTextSelected: { 
    color: "#fff" 
  },

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
  cardTitle: { 
    fontSize: 16, 
    fontWeight: "bold", 
    marginBottom: 6 
  },
  cardText: { 
    fontSize: 14, 
    color: "#444", 
    marginBottom: 12 
  },

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
  tagText: { 
    fontSize: 12, 
    fontWeight: "bold", 
    color: "#1A73E8" 
  },
  timeText: { 
    fontSize: 12, 
    color: "#777" 
  },

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
