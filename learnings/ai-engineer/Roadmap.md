## Phase 0: Mental model shift (1 week)
"AI Engineer" ≠ "ML Engineer." You're not training models from scratch — you're building applications *on top of* LLMs (OpenAI, Anthropic, open-source models). Your job: prompt design, RAG pipelines, agent orchestration, vector databases, evaluation, and deploying all of it reliably — which is exactly your existing microservices skillset with new pieces bolted in.

## Phase 1: Python fluency (2–3 weeks)
You don't need to master Python, just get comfortable — most AI tooling (LangChain, LlamaIndex, HuggingFace) is Python-first.
- **YouTube:** freeCodeCamp's "Python for Everybody" or Programming with Mosh's Python course — skim fast since you already know Java/JS concepts; focus on syntax, `venv`, `pip`, async in Python, and Pydantic (used everywhere in AI tooling).
- Skip basic CS concepts entirely — you already know them.

## Phase 2: LLM fundamentals & prompt engineering (2 weeks)
- **Concepts:** tokens, context windows, temperature, system/user/assistant roles, function calling/tool use, structured outputs, embeddings.
- **YouTube:** Andrej Karpathy's "Intro to Large Language Models" and "Deep Dive into LLMs like ChatGPT" — the best free explanation of how these models actually work, no fluff.
- **Practice:** Anthropic's and OpenAI's own prompting docs/cookbooks (free, and more current than any course).

## Phase 3: Core AI Engineering stack (4–6 weeks)
This is the heart of it — RAG, vector DBs, agents, LangChain/LlamaIndex.
- **Udemy — pick ONE broad bootcamp, don't buy 5:**
  - *"The AI Engineer Course 2026: Complete AI Engineer Bootcamp"* — well-reviewed, ~30 hrs, covers Python/LLM fundamentals → NLP → transformers → LangChain → RAG → vector DBs (Pinecone) → prompt engineering → agents. Good single starting point. It covers the full AI engineering pipeline: Python and LLM fundamentals, NLP, transformers, LangChain, RAG systems, vector databases with Pinecone, prompt engineering, and building AI agents, with a focus on breadth over just calling an API.
  - *"LLM Engineering: Master AI, Large Language Models & Agents"* (Ed Donner/Ligency) — a large, highly rated course (300,000+ students, 4.7 stars) from instructors known for practical, no-filler teaching. Good second course once you know the basics, for deeper production patterns.
- **YouTube (free, in order):**
  1. LangChain's official YouTube channel — RAG and agent tutorials, updated frequently.
  2. "Vector databases explained" (search current videos from Pinecone or Weaviate's own channels — vendor content, but genuinely instructive).
  3. Sam Witteveen or James Briggs on YouTube — practical LangChain/RAG walkthroughs.

## Phase 4: Agentic AI (2–3 weeks) — this is where 2026 hiring is
- **Udemy:** *"AI Engineer Core Track: LLM Engineering, RAG, QLoRA, Agents"* — a deep dive into generative AI and RAG systems with hands-on applications, best for engineers who want depth in LLM use cases and production patterns.
- Learn: multi-agent frameworks (LangGraph, CrewAI, or AutoGen), tool-calling patterns, agent memory, guardrails/evaluation.
- **YouTube:** LangGraph's own tutorials for stateful multi-step agents — very relevant since you already think in terms of workflows/state machines from Kafka/microservices.

## Phase 5: Bring it home to your actual stack (3–4 weeks) — most important phase
This is where you differentiate from generic "AI course" grads:
- Build a Spring Boot service that calls an LLM API, does RAG over your own docs (Pinecone/Weaviate/pgvector), and exposes it via REST — this is your bridge project.
- Explore **Spring AI** (Spring's official LLM integration framework) — directly relevant to your stack, lets you stay in Java for the backend while doing AI work.
- Deploy it on your existing K8s/OpenShift knowledge — AI workloads still need the same reliability patterns (circuit breakers via Resilience4j, observability via ELK) you already know.
- Add a React 18 chat UI on top — you already have this skill.

## Suggested weekly cadence (given you're working full-time + job hunting)
- Weeks 1–2: Python + LLM fundamentals (Phase 0–2)
- Weeks 3–6: Core bootcamp (Phase 3)
- Weeks 7–9: Agentic AI (Phase 4)
- Weeks 10–13: Your bridge project — Spring Boot + Spring AI + RAG + React (Phase 5) — **this becomes a portfolio piece and resume bullet**

One practical note: don't buy multiple overlapping Udemy bootcamps — reviewers note real overlap between big-name courses since the same instructor teams (e.g., Ed Donner/Ligency) produce several of the top-selling ones, so one solid bootcamp + free YouTube/vendor docs covers you well.
